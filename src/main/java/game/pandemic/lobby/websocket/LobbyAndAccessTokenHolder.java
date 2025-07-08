package game.pandemic.lobby.websocket;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LobbyAndAccessTokenHolder {
    @JsonView(JacksonView.Read.class)
    private final Lobby lobby;
    @JsonView(JacksonView.Read.class)
    private final UUID accessToken;
}
