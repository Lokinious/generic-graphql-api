package lokinious.graphql_api.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;
import java.util.Map;

@Controller("/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {
    
    @Post("/login")
    public Mono<Map<String, String>> login(@Body UsernamePasswordCredentials credentials) {
        // In a real application, you would validate credentials against a database
        if ("admin".equals(credentials.getUsername()) && "password".equals(credentials.getPassword())) {
            return Mono.just(Map.of(
                "status", "success",
                "message", "Login successful",
                "username", credentials.getUsername()
            ));
        }
        return Mono.just(Map.of(
            "status", "error",
            "message", "Invalid credentials"
        ));
    }
}
