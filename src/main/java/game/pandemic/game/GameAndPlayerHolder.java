package game.pandemic.game;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.game.player.Player;
import game.pandemic.jackson.IJsonTypeInfo;
import game.pandemic.jackson.JacksonView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameAndPlayerHolder implements IJsonTypeInfo {
    @JsonView(JacksonView.Read.class)
    private final Game game;
    @JsonView(JacksonView.Read.class)
    private final Player player;
}
