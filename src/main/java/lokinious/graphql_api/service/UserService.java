package lokinious.graphql_api.service;

import lokinious.graphql_api.model.User;
import lokinious.graphql_api.repository.UserRepository;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@Requires(beans = UserRepository.class)
public class UserService {
    
    private final UserRepository userRepository;
    
    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    
    public Mono<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Mono<User> createUser(String username, String email, String firstName, String lastName) {
        User user = new User(username, email, firstName, lastName);
        return userRepository.save(user);
    }
}
