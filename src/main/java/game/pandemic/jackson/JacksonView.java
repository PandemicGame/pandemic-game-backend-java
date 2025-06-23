package game.pandemic.jackson;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class JacksonView {
    public interface Any {}
    public interface Create extends Any {}
    public interface Read extends Any {}
    public interface Update extends Any {}
    public interface Delete extends Any {}
}