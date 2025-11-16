package game.pandemic.game.websocket.endpoints;

import game.pandemic.game.GameService;
import game.pandemic.game.player.Player;
import game.pandemic.game.player.PlayerRepository;
import game.pandemic.game.websocket.GameWebSocketControllerEndpoint;
import game.pandemic.jackson.ObjectMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class RejectEffect extends GameWebSocketControllerEndpoint {
    public RejectEffect(final ObjectMapper objectMapper, final GameService gameService, final PlayerRepository playerRepository) {
        super(objectMapper, gameService, playerRepository);
    }

    @Override
    public String getEndpointMapping() {
        return "/reject-effect";
    }

    @Override
    public void consume(final Player player, final String message) {
        if (NumberUtils.isCreatable(message)) {
            this.gameService.rejectEffect(player, Long.parseLong(message));
        }
    }
}
