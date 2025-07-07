package game.pandemic.chat.websocket.controllers;

import game.pandemic.chat.websocket.IChatWebSocketControllerEndpoint;
import game.pandemic.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserChatWebSocketController extends ChatWebSocketController<User> {
    public UserChatWebSocketController(final List<IChatWebSocketControllerEndpoint<User>> webSocketControllerEndpoints) {
        super(new ArrayList<>(webSocketControllerEndpoints));
    }
}
