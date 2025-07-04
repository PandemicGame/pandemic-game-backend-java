package game.pandemic.websocket.message;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WebSocketMessageType {
    AUTH,
    MESSAGE;

    @JsonCreator
    public static WebSocketMessageType fromString(final String key) {
        return key == null ? null : WebSocketMessageType.valueOf(key.toUpperCase());
    }
}
