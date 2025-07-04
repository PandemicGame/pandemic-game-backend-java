package game.pandemic.websocket;

import game.pandemic.config.CorsConfig;
import game.pandemic.websocket.endpoint.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.List;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    public static final String WEBSOCKET_PREFIX = "/ws";

    private final CorsConfig corsConfig;
    private final List<WebSocketHandler<?>> webSocketHandlers;

    @Override
    public void registerWebSocketHandlers(@NonNull final WebSocketHandlerRegistry registry) {
        final String[] allowedOrigins = this.corsConfig.getAllowedOrigins();
        for (final WebSocketHandler<?> webSocketHandler : this.webSocketHandlers) {
            registry
                    .addHandler(webSocketHandler, WEBSOCKET_PREFIX + webSocketHandler.getEndpointMapping())
                    .setAllowedOrigins(allowedOrigins);
        }
    }
}
