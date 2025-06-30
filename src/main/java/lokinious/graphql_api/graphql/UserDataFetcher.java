package lokinious.graphql_api.graphql;

import lokinious.graphql_api.model.User;
import lokinious.graphql_api.service.UserService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
@Requires(property = "graphql.enabled", value = "true", defaultValue = "true")
@Requires(beans = UserService.class)
public class UserDataFetcher implements DataFetcher<CompletableFuture<Object>> {
    
    private final UserService userService;
    
    @Inject
    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public CompletableFuture<Object> get(DataFetchingEnvironment environment) throws Exception {
        String fieldName = environment.getField().getName();
        
        switch (fieldName) {
            case "users":
                return userService.getAllUsers()
                        .collectList()
                        .map(users -> (Object) users)
                        .toFuture();
                        
            case "user":
                String id = environment.getArgument("id");
                if (id != null) {
                    return userService.getUserById(id)
                            .map(user -> (Object) user)
                            .toFuture();
                }
                String username = environment.getArgument("username");
                if (username != null) {
                    return userService.getUserByUsername(username)
                            .map(user -> (Object) user)
                            .toFuture();
                }
                return CompletableFuture.completedFuture(null);
                
            case "createUser":
                Map<String, Object> input = environment.getArgument("input");
                return userService.createUser(
                        (String) input.get("username"),
                        (String) input.get("email"),
                        (String) input.get("firstName"),
                        (String) input.get("lastName")
                ).map(user -> (Object) user)
                .toFuture();
                
            default:
                return CompletableFuture.completedFuture(null);
        }
    }
}
