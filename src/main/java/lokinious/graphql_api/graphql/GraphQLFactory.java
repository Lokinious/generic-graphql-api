package lokinious.graphql_api.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Factory
@Requires(property = "graphql.enabled", value = "true", defaultValue = "true")
@Requires(beans = UserDataFetcher.class)
public class GraphQLFactory {
    
    private final UserDataFetcher userDataFetcher;
    private final ResourceResolver resourceResolver;
    
    @Inject
    public GraphQLFactory(UserDataFetcher userDataFetcher, ResourceResolver resourceResolver) {
        this.userDataFetcher = userDataFetcher;
        this.resourceResolver = resourceResolver;
    }
    
    @Bean
    @Singleton
    public GraphQL graphQL() throws IOException {
        return GraphQL.newGraphQL(buildSchema()).build();
    }
    
    private GraphQLSchema buildSchema() throws IOException {
        // Parse the schema
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(loadSchemaFile());
        
        // Build the runtime wiring
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("users", userDataFetcher)
                        .dataFetcher("user", userDataFetcher))
                .type("Mutation", builder -> builder
                        .dataFetcher("createUser", userDataFetcher))
                .build();
        
        // Generate the schema
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }
    
    private String loadSchemaFile() throws IOException {
        InputStream inputStream = resourceResolver.getResourceAsStream("classpath:schema.graphql")
                .orElseThrow(() -> new RuntimeException("schema.graphql not found"));
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
