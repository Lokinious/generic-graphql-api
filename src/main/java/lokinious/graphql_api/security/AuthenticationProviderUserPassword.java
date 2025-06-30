package lokinious.graphql_api.security;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
@Replaces(AuthenticationProvider.class)
public class AuthenticationProviderUserPassword implements AuthenticationProvider<HttpRequest<?>> {

    @Override
    public Mono<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, 
                                                   AuthenticationRequest<?, ?> authenticationRequest) {
        String identity = authenticationRequest.getIdentity().toString();
        String secret = authenticationRequest.getSecret().toString();
        
        // Simple authentication - in production, you would validate against a database
        if (identity.equals("admin") && secret.equals("password")) {
            return Mono.just(AuthenticationResponse.success(identity));
        }
        
        return Mono.just(AuthenticationResponse.failure());
    }
}
