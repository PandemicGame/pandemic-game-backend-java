package game.pandemic.event;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Event<C extends IEventContext<C, E>, E extends Event<C, E>> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    private Long id;
    @Setter
    @JsonView(JacksonView.Read.class)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected E nextEvent;

    public abstract void apply(final C context);

    public void applyAll(final C context) {
        this.apply(context);
        if (hasNextEvent()) {
            this.nextEvent.applyAll(context);
        }
    }

    public boolean hasNextEvent() {
        return this.nextEvent != null;
    }

    public void appendEvent(final E event) {
        if (hasNextEvent()) {
            this.nextEvent.appendEvent(event);
        } else {
            this.nextEvent = event;
        }
    }

    public Event<C, E> getLastEvent() {
        if (hasNextEvent()) {
            return this.nextEvent.getLastEvent();
        } else {
            return this;
        }
    }
}
