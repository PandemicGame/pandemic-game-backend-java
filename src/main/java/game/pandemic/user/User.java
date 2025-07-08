package game.pandemic.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.auth.Account;
import game.pandemic.chat.ChatMessageSender;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class User extends ChatMessageSender implements IWebSocketAuthenticationObject {
    @Setter
    @Column(unique = true)
    @JsonIgnore
    private UUID accessToken;
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

    @JsonIgnore
    public String getAccessTokenString() {
        return this.accessToken.toString();
    }
}
