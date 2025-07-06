package game.pandemic.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.SIMPLE_NAME,
        property = "@class"
)
public interface IJsonTypeInfo {
}
