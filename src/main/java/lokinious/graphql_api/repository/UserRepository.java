package lokinious.graphql_api.repository;

import lokinious.graphql_api.model.User;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.micronaut.context.annotation.Requirements;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.mongodb.client.model.Filters.eq;

@Singleton
@Requires(beans = MongoClient.class)
public class UserRepository {
    
    private final MongoCollection<Document> collection;
    
    @Inject
    public UserRepository(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("generic-graphql-api");
        this.collection = database.getCollection("users");
    }
    
    public Mono<User> save(User user) {
        Document doc = new Document()
                .append("username", user.username())
                .append("email", user.email())
                .append("firstName", user.firstName())
                .append("lastName", user.lastName());
        
        return Mono.from(collection.insertOne(doc))
                .map(result -> {
                    String newId = result.getInsertedId().asObjectId().getValue().toString();
                    return user.withId(newId);
                });
    }
    
    public Flux<User> findAll() {
        return Flux.from(collection.find())
                .map(this::documentToUser);
    }
    
    public Mono<User> findById(String id) {
        return Mono.from(collection.find(eq("_id", new org.bson.types.ObjectId(id))))
                .map(this::documentToUser);
    }
    
    public Mono<User> findByUsername(String username) {
        return Mono.from(collection.find(eq("username", username)))
                .map(this::documentToUser);
    }
    
    private User documentToUser(Document doc) {
        String id = doc.getObjectId("_id").toString();
        String username = doc.getString("username");
        String email = doc.getString("email");
        String firstName = doc.getString("firstName");
        String lastName = doc.getString("lastName");
        
        return new User(id, username, email, firstName, lastName);
    }
}
