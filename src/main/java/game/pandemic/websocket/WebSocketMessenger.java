package game.pandemic.websocket;

import game.pandemic.jackson.JacksonView;
import game.pandemic.jackson.ObjectMapper;
import game.pandemic.messaging.messengers.IUnicastAndMulticastMessenger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log4j2
public class WebSocketMessenger implements IUnicastAndMulticastMessenger<WebSocketSession> {
    private final ObjectMapper objectMapper;

    @Override
    public boolean unicast(final WebSocketSession target, final Object message, final Class<? extends JacksonView.Any> view) {
        return this.objectMapper.serialize(message, view, s -> unicast(target, s), () -> false);
    }

    @Override
    public boolean unicast(final WebSocketSession target, final Object message) {
        return this.objectMapper.serialize(message, s -> unicast(target, s), () -> false);
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
    public Map<WebSocketSession, Boolean> multicast(final Set<WebSocketSession> targets, final Object message, final Class<? extends JacksonView.Any> view) {
        return this.objectMapper.serialize(message, view, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    @Override
    public Map<WebSocketSession, Boolean> multicast(final Set<WebSocketSession> targets, final Object message) {
        return this.objectMapper.serialize(message, s -> multicast(targets, s), () -> multicastFailure(targets));
    }

    @Override
    public Map<WebSocketSession, Boolean> multicast(final Set<WebSocketSession> targets, final String message) {
        return multicastEvaluation(targets, t -> unicast(t, message));
    }
}
