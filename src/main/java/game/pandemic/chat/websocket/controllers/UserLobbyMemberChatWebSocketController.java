package game.pandemic.chat.websocket.controllers;

import game.pandemic.chat.websocket.IChatWebSocketControllerEndpoint;
import game.pandemic.lobby.member.UserLobbyMember;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserLobbyMemberChatWebSocketController extends ChatWebSocketController<UserLobbyMember> {
    public UserLobbyMemberChatWebSocketController(final List<IChatWebSocketControllerEndpoint<UserLobbyMember>> webSocketControllerEndpoints) {
        super(new ArrayList<>(webSocketControllerEndpoints));
    }
}
