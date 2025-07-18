package game.pandemic.game.board.type;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.IJsonTypeInfo;
import game.pandemic.jackson.JacksonView;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardSettings implements IJsonTypeInfo {
    @JsonView(JacksonView.Read.class)
    private int boardSlotRadius;
    @JsonView(JacksonView.Read.class)
    private double boardSlotLabelOffset;
    @JsonView(JacksonView.Read.class)
    private int maxZoom;
    @JsonView(JacksonView.Read.class)
    private int minZoom;
    @JsonView(JacksonView.Read.class)
    private int defaultZoom;
    @JsonView(JacksonView.Read.class)
    private double centerX;
    @JsonView(JacksonView.Read.class)
    private double centerY;
    @JsonView(JacksonView.Read.class)
    private double boundsXMin;
    @JsonView(JacksonView.Read.class)
    private double boundsXMax;
    @JsonView(JacksonView.Read.class)
    private double boundsYMin;
    @JsonView(JacksonView.Read.class)
    private double boundsYMax;
}
