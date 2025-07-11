package game.pandemic.lobby.websocket;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.IJsonTypeInfo;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LobbyAndAccessTokenHolder implements IJsonTypeInfo {
    @JsonView(JacksonView.Read.class)
    private final Lobby lobby;
    @JsonView(JacksonView.Read.class)
    private final String accessToken;
}
