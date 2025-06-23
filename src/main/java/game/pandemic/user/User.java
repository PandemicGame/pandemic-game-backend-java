package game.pandemic.user;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.auth.Account;
import game.pandemic.jackson.JacksonView;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = User.TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    public static final String TABLE_NAME = "application_user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Any.class)
    private Long id;
    @Setter
    @Column(unique = true)
    private UUID accessToken;
    @JsonView(JacksonView.Any.class)
    private String name;
    @OneToOne
    private Account account;

    public User(final String name) {
        this.name = name;
    }

    public User(final Account account) {
        this.name = account.getUsername();
        this.account = account;
    }

    public String getAccessTokenString() {
        return this.accessToken.toString();
    }
}
