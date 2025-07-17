package game.pandemic.game.websocket;

import game.pandemic.game.GameService;
import game.pandemic.game.player.Player;
import game.pandemic.game.player.PlayerRepository;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.endpoint.IWebSocketEndpointConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GameWebSocketControllerEndpoint implements IGameWebSocketControllerEndpoint, IWebSocketEndpointConsumer<UserLobbyMember> {
    protected final ObjectMapper objectMapper;
    protected final GameService gameService;
    protected final PlayerRepository playerRepository;

    @Override
    public void consume(final WebSocketSession session, final UserLobbyMember userLobbyMember, final String message) {
        this.playerRepository.findByLobbyMember(userLobbyMember).ifPresent(player -> consume(player, message));
    }

    public abstract void consume(final Player player, final String message);
}
