package game.pandemic.lobby.websocket.endpoints;

import game.pandemic.game.GameOptions;
import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.lobby.LobbyService;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.websocket.LobbyWebSocketControllerEndpoint;
import game.pandemic.messaging.messengers.IUnicastMessenger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UpdateGameOptions extends LobbyWebSocketControllerEndpoint<UserLobbyMember> {
    public UpdateGameOptions(final ObjectMapper objectMapper, final LobbyService lobbyService, final IUnicastMessenger<WebSocketSession> webSocketSessionMessenger) {
        super(objectMapper, lobbyService, webSocketSessionMessenger);
    }

    @Override
    public String getEndpointMapping() {
        return "/game-options";
    }

    @Override
    public void consume(final WebSocketSession session, final UserLobbyMember userLobbyMember, final String message) {
        this.objectMapper.deserialize(
                message,
                GameOptions.class,
                JacksonView.Update.class,
                gameOptions -> this.lobbyService.updateOptions(userLobbyMember, gameOptions)
        );
    }
}
