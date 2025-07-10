package game.pandemic.websocket.auth;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "access-token")
@Getter
@Setter
public class AccessTokenConfig {
    private String secretKey;

    @Bean
    public SecretKey jwtSigningKey() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }
}
