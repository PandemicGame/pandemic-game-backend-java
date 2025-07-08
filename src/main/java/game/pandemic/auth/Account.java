package game.pandemic.auth;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"issuer", "subject"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonView(JacksonView.AuthorizedRead.class)
    private String firstName;
    @JsonView(JacksonView.AuthorizedRead.class)
    private String lastName;
    @JsonView(JacksonView.AuthorizedRead.class)
    private String username;
    @JsonView(JacksonView.AuthorizedRead.class)
    private String email;
    @JsonView(JacksonView.AuthorizedRead.class)
    private URL issuer;
    @JsonView(JacksonView.AuthorizedRead.class)
    private String subject;

    public Account(final String firstName, final String lastName, final String username, final String email, final URL issuer, final String subject) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.issuer = issuer;
        this.subject = subject;
    }
}
