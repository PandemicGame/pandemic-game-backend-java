package game.pandemic.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtIssuerUriBasedAuthenticationManagerResolver implements AuthenticationManagerResolver<HttpServletRequest> {
    private final AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;
    private final JwtIssuerConfig jwtIssuerConfig;
    private final JwtToAccountConverter jwtToAccountConverter;

    public JwtIssuerUriBasedAuthenticationManagerResolver(final JwtIssuerConfig jwtIssuerConfig, final JwtToAccountConverter jwtToAccountConverter) {
        this.jwtIssuerConfig = jwtIssuerConfig;
        this.jwtToAccountConverter = jwtToAccountConverter;
        final Map<String, AuthenticationManager> authenticationManagerMap = getAuthenticationManagerMap();
        this.authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(authenticationManagerMap::get);
    }

    private Map<String, AuthenticationManager> getAuthenticationManagerMap() {
        return this.jwtIssuerConfig.getJwtIssuerUris().stream()
                .map(this::getJwtIssuerUriToAuthenticationManagerAssociation)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, AuthenticationManager> getJwtIssuerUriToAuthenticationManagerAssociation(final String issuerUri) {
        final JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        final JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        authenticationProvider.setJwtAuthenticationConverter(this.jwtToAccountConverter);
        return new AbstractMap.SimpleEntry<>(issuerUri, authenticationProvider::authenticate);
    }

    @Override
    public AuthenticationManager resolve(final HttpServletRequest context) {
        return this.authenticationManagerResolver.resolve(context);
    }
}
