package game.pandemic.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.serialization.IUnicastAndMulticastMessengerWithSerialization;
import game.pandemic.messaging.messengers.serialization.MessengerWithSerialization;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
@Log4j2
public class WebSocketMessenger extends MessengerWithSerialization<WebSocketSession> implements IUnicastAndMulticastMessengerWithSerialization<WebSocketSession> {
    public WebSocketMessenger(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean unicast(final WebSocketSession target, final String message) {
        try {
            target.sendMessage(new TextMessage(message));
            return true;
        } catch (final IOException e) {
            log.warn("An Exception occurred during sending of message to session: " + target.getId());
            return false;
        }
    }
}
