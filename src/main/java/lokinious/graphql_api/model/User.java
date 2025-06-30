package lokinious.graphql_api.model;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity
public record User(
    @Id @GeneratedValue String id,
    String username,
    String email,
    String firstName,
    String lastName
) {
    
    // Constructor for creating new users without ID (ID will be generated)
    public User(String username, String email, String firstName, String lastName) {
        this(null, username, email, firstName, lastName);
    }
    
    // Helper method to create a copy with a new ID (useful for database operations)
    public User withId(String newId) {
        return new User(newId, username, email, firstName, lastName);
    }
}
