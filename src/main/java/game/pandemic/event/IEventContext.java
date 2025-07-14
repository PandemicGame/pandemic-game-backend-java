package game.pandemic.event;

public interface IEventContext<C extends IEventContext<C, E>, E extends Event<C, E>> {
    void processEvent(final E event);
    void reset();
    void restore();
    default void resetAndRestore() {
        reset();
        restore();
    }
}
