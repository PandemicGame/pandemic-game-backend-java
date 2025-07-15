package game.pandemic.lobby.websocket.controllers;

import game.pandemic.lobby.member.UserLobbyMember;
import game.pandemic.lobby.websocket.ILobbyWebSocketControllerEndpoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserLobbyMemberLobbyWebSocketController extends LobbyWebSocketController<UserLobbyMember> {
    public UserLobbyMemberLobbyWebSocketController(final List<ILobbyWebSocketControllerEndpoint<UserLobbyMember>> webSocketControllerEndpoints) {
        super(new ArrayList<>(webSocketControllerEndpoints));
    }
}
