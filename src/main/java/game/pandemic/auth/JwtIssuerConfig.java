package game.pandemic.auth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Getter
public class JwtIssuerConfig {
    @Value("#{'${jwt.issuer-uris}'.split(',')}")
    private List<String> jwtIssuerUris;
}
