package game.pandemic.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtToAccountConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    public static final String FIRST_NAME_CLAIM = "given_name";
    public static final String LAST_NAME_CLAIM = "family_name";
    public static final String USERNAME_CLAIM = "preferred_username";
    public static final String EMAIL_CLAIM = "email";
    public static final String ROLES_CLAIM = "roles";

    private final AccountRepository accountRepository;

    @Override
    public @NonNull AbstractAuthenticationToken convert(final Jwt jwt) {
        final String subject = jwt.getSubject();
        final URL issuer = jwt.getIssuer();
        final List<String> roles = jwt.getClaimAsStringList(ROLES_CLAIM);

        final Account account = this.accountRepository.findAccountBySubjectAndIssuer(subject, issuer)
                .orElseGet(() -> this.accountRepository.save(createNewAccountFromJwt(jwt)));

        final List<GrantedAuthority> authorities = extractAuthoritiesFromRoles(roles);

        return new UsernamePasswordAuthenticationToken(account, jwt, authorities);
    }

    private Account createNewAccountFromJwt(final Jwt jwt) {
        final String subject = jwt.getSubject();
        final URL issuer = jwt.getIssuer();
        final String firstName = jwt.getClaimAsString(FIRST_NAME_CLAIM);
        final String lastName = jwt.getClaimAsString(LAST_NAME_CLAIM);
        final String username = jwt.getClaimAsString(USERNAME_CLAIM);
        final String email = jwt.getClaimAsString(EMAIL_CLAIM);
        return new Account(firstName, lastName, username, email, issuer, subject);
    }

    private List<GrantedAuthority> extractAuthoritiesFromRoles(final List<String> roles) {
        if (roles != null) {
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }
}
