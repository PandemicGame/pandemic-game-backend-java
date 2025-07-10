package game.pandemic.user;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.auth.Account;
import game.pandemic.chat.ChatMessageSender;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class User extends ChatMessageSender implements IWebSocketAuthenticationObject {
    @OneToOne
    @JsonView(JacksonView.AuthorizedRead.class)
    private Account account;

    public User(final String name) {
        super(name);
    }

    public User(final Account account) {
        this(account.getUsername());
        this.account = account;
    }
}
