package game.pandemic.game.websocket.controllers;

import game.pandemic.game.websocket.IGameWebSocketControllerEndpoint;
import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.websocket.endpoint.WebSocketController;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameWebSocketController extends WebSocketController<UserLobbyMember> {
    public GameWebSocketController(final List<IGameWebSocketControllerEndpoint> webSocketControllerEndpoints) {
        super(new ArrayList<>(webSocketControllerEndpoints));
    }

    @Override
    public String getEndpointMapping() {
        return "/game";
    }
}
