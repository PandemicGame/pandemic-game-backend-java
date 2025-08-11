package game.pandemic.game;

import game.pandemic.game.board.location.LocationService;
import game.pandemic.game.board.type.BoardType;
import game.pandemic.game.board.type.BoardTypeRepository;
import game.pandemic.game.board.type.BoardTypeService;
import game.pandemic.game.events.CreateGameEvent;
import game.pandemic.game.plague.PlagueService;
import game.pandemic.game.player.Player;
import game.pandemic.game.role.LobbyMemberRoleAssociation;
import game.pandemic.game.role.Role;
import game.pandemic.game.role.RoleRepository;
import game.pandemic.game.role.RoleService;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import game.pandemic.lobby.events.StartGameLobbyEvent;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class GameService {
    private final BoardTypeRepository boardTypeRepository;
    private final BoardTypeService boardTypeService;
    private final GameRepository gameRepository;
    private final LocationService locationService;
    private final PlagueService plagueService;
    private final IUnicastMessenger<Player> playerMessenger;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final Random random;

    @PostConstruct
    private void postConstruct() {
        this.plagueService.createAllPlagues();

        this.locationService.createAllLocations();
        this.boardTypeService.createAllBoardTypes();

        this.roleService.createAllRoles();
    }

    public GameOptions createDefaultGameOptions() {
        final GameOptions gameOptions = new GameOptions();
        addChoicesToGameOptions(gameOptions);
        addDefaultChoicesToGameOptions(gameOptions);
        return gameOptions;
    }

    public void addChoicesToGameOptions(final GameOptions gameOptions) {
        gameOptions.setAvailableBoardTypes(this.boardTypeRepository.findAll());
    }

    private void addDefaultChoicesToGameOptions(final GameOptions gameOptions) {
        final List<BoardType> boardTypes = gameOptions.getAvailableBoardTypes();
        if (!boardTypes.isEmpty()) {
            gameOptions.setSelectedBoardTypeId(boardTypes.get(0).getId());
        }
    }

    public void startGameInLobby(final Lobby lobby, final UnaryOperator<Lobby> callback) {
        createStartGameLobbyEvent(lobby).ifPresent(startGameLobbyEvent -> {
            lobby.processEvent(startGameLobbyEvent);
            final Lobby saved = callback.apply(lobby);

            createGame(saved);
        });
    }

    private Optional<StartGameLobbyEvent> createStartGameLobbyEvent(final Lobby lobby) {
        final GameOptions gameOptions = lobby.getGameOptions();

        final Optional<BoardType> boardTypeOptional = this.boardTypeRepository.findById(gameOptions.getSelectedBoardTypeId());
        if (boardTypeOptional.isEmpty()) {
            return Optional.empty();
        }

        final BoardType boardType = boardTypeOptional.get();
        final List<LobbyMemberRoleAssociation> lobbyMemberRoleAssociations = createLobbyMemberRoleAssociations(lobby);
        final StartGameLobbyEvent startEvent = new StartGameLobbyEvent(boardType, lobbyMemberRoleAssociations);

        return Optional.of(startEvent);
    }

    private void createGame(final Lobby lobby) {
        if (lobby.getCreationEvent().getLastEvent() instanceof StartGameLobbyEvent startGameLobbyEvent) {
            final CreateGameEvent createGameEvent = startGameLobbyEvent.getCreateGameEvent();
            final Game game = this.gameRepository.save(createGameEvent.createGame());
            sendGameAndPlayerHolderToPlayers(game);
        }
    }

    private List<LobbyMemberRoleAssociation> createLobbyMemberRoleAssociations(final Lobby lobby) {
        final List<Role> allRoles = new ArrayList<>(this.roleRepository.findAll());

        return lobby.getMembers().stream()
                .map(member -> new LobbyMemberRoleAssociation(
                        member,
                        getAndRemoveRandomRole(allRoles)
                ))
                .toList();
    }

    private Role getAndRemoveRandomRole(final List<Role> allRoles) {
        return allRoles.remove(this.random.nextInt(allRoles.size()));
    }

    private void sendGameAndPlayerHolderToPlayers(final Game game) {
        game.getPlayersInTurnOrder().forEach(player -> sendGameAndPlayerHolderToPlayer(player, game));
    }

    private void sendGameAndPlayerHolderToPlayer(final Player player, final Game game) {
        this.playerMessenger.unicast(
                player,
                new GameAndPlayerHolder(
                        game,
                        player
                ),
                JacksonView.Read.class
        );
    }
}
