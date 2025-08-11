package game.pandemic.game.websocket.endpoints;

import game.pandemic.game.GameService;
import game.pandemic.game.player.Player;
import game.pandemic.game.player.PlayerRepository;
import game.pandemic.game.websocket.GameWebSocketControllerEndpoint;
import game.pandemic.jackson.ObjectMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class ExecuteActionEffect extends GameWebSocketControllerEndpoint {
    public ExecuteActionEffect(final ObjectMapper objectMapper, final GameService gameService, final PlayerRepository playerRepository) {
        super(objectMapper, gameService, playerRepository);
    }

    @Override
    public String getEndpointMapping() {
        return "/execute-action-effect";
    }

    @Override
    public void consume(final Player player, final String message) {
        if (NumberUtils.isCreatable(message)) {
            this.gameService.executeActionEffect(player, Long.parseLong(message));
        }
    }
}
