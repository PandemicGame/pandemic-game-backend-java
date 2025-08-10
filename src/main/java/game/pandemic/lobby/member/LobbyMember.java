package game.pandemic.lobby.member;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.chat.ChatMessageSender;
import game.pandemic.jackson.JacksonView;
import game.pandemic.lobby.Lobby;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class LobbyMember extends ChatMessageSender {
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    protected Lobby lobby;

    protected LobbyMember(final String name) {
        super(name);
    }
}
