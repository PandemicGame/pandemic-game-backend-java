package game.pandemic.lobby.websocket.controllers;

import game.pandemic.lobby.websocket.ILobbyWebSocketControllerEndpoint;
import game.pandemic.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserLobbyWebSocketController extends LobbyWebSocketController<User> {
    public UserLobbyWebSocketController(final List<ILobbyWebSocketControllerEndpoint<User>> webSocketControllerEndpoints) {
        super(new ArrayList<>(webSocketControllerEndpoints));
    }
}
