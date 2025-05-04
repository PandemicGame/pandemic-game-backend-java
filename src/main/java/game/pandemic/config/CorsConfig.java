package game.pandemic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class CorsConfig {
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", getCorsConfiguration());
        return urlBasedCorsConfigurationSource;
    }

    private CorsConfiguration getCorsConfiguration() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(getAllowedOrigins());
        corsConfiguration.setAllowedMethods(getAllowedMethods());
        corsConfiguration.setAllowedHeaders(getAllowedHeaders());
        return corsConfiguration;
    }

    private List<String> getAllowedOrigins() {
        return Arrays.asList(this.allowedOrigins);
    }

    private List<String> getAllowedMethods() {
        return Stream.of(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.DELETE
        ).map(Object::toString).toList();
    }

    private List<String> getAllowedHeaders() {
        return List.of(
                HttpHeaders.AUTHORIZATION
        );
    }
}
