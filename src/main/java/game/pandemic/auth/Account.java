package game.pandemic.auth;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"issuer", "subject"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private URL issuer;
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
