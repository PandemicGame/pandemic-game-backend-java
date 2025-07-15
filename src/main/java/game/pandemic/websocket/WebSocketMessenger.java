package game.pandemic.websocket;

import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.persistent.IUnicastAndMulticastPersistentConnectionMessenger;
import game.pandemic.messaging.messengers.serialization.IUnicastAndMulticastMessengerWithSerialization;
import game.pandemic.messaging.messengers.serialization.MessengerWithSerialization;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class WebSocketMessenger extends MessengerWithSerialization<WebSocketSession> implements IUnicastAndMulticastMessengerWithSerialization<WebSocketSession>, IUnicastAndMulticastPersistentConnectionMessenger<WebSocketSession> {
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

    @Override
    public boolean closeConnection(final WebSocketSession target) {
        return closeConnection(target, CloseStatus.NORMAL);
    }

    @Override
    public boolean closeConnection(final WebSocketSession target, final String message) {
        return closeConnection(target, CloseStatus.NORMAL.withReason(message));
    }

    public boolean closeConnection(final WebSocketSession target, final CloseStatus closeStatus) {
        try {
            target.close(closeStatus);
            return true;
        } catch (final IOException e) {
            log.warn("An exception occurred during closing of session: " + target.getId());
            return false;
        }
    }

    @Override
    public Map<WebSocketSession, Boolean> closeConnection(final Set<WebSocketSession> targets) {
        return closeConnection(targets, CloseStatus.NORMAL);
    }

    @Override
    public Map<WebSocketSession, Boolean> closeConnection(final Set<WebSocketSession> targets, final String message) {
        return closeConnection(targets, CloseStatus.NORMAL.withReason(message));
    }

    public Map<WebSocketSession, Boolean> closeConnection(final Set<WebSocketSession> targets, final CloseStatus closeStatus) {
        return targets.stream()
                .map(target -> new AbstractMap.SimpleEntry<>(target, closeConnection(target, closeStatus)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
