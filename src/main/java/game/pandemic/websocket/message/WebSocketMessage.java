package game.pandemic.websocket.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class WebSocketMessage {
    @JsonProperty
    @NotNull
    private WebSocketMessageType messageType;
    @JsonProperty
    @Pattern(regexp = "^(/[\\w-]+)+$")
    private String destination;
    private String payload;

    @JsonProperty("payload")
    public void setPayloadString(final JsonNode jsonNode) {
        if (jsonNode.isTextual()) {
            this.payload = jsonNode.asText();
        } else {
            this.payload = jsonNode.toString();
        }
    }
}
