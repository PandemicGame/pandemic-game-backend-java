package game.pandemic.websocket.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Log4j2
public abstract class AccessTokenService<A extends IWebSocketAuthenticationObject> {
    /**
     * The default expiration time in milliseconds is 1 year.
     */
    public static final long EXPIRATION_TIME_MILLISECONDS = 31_557_600_000L;

    protected final SecretKey key;
    protected final IWebSocketAuthenticationObjectRepository<A> repository;

    public String createAccessTokenForObject(final A object) {
        try {
            return Jwts.builder()
                    .subject(object.getId().toString())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLISECONDS))
                    .signWith(this.key)
                    .compact();
        } catch (final JwtException e) {
            log.warn("Failed to create access token for object of type " + object.getClass() + " with id: " + object.getId());
            return "";
        }
    }

    public Optional<A> parseAccessTokenToObject(final String accessToken) {
        try {
            final Claims claims = Jwts.parser()
                    .verifyWith(this.key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

            final String subjectClaim = claims.getSubject();
            final Long id = Long.parseLong(subjectClaim);
            return this.repository.findById(id);
        } catch (final JwtException | IllegalArgumentException e) {
            log.warn("Invalid or expired access token");
            return Optional.empty();
        }
    }
}
