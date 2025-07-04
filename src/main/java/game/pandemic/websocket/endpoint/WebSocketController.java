package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public abstract class WebSocketController<A extends IWebSocketAuthenticationObject> implements IWebSocketController<A> {
    protected final List<IWebSocketControllerEndpoint<A>> controllerEndpoints;

    @Override
    public List<IWebSocketControllerEndpoint<A>> getAllEndpoints() {
        return this.controllerEndpoints;
    }

    @Override
    public void noValidEndpointsForPathHandler(final String path) {
        log.warn("There is no endpoint at path: \"" + path + "\"");
    }
}
