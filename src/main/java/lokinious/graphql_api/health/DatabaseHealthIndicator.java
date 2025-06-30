package lokinious.graphql_api.health;

import io.micronaut.health.HealthStatus;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;
import java.util.Map;

@Singleton
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Override
    public Mono<HealthResult> getResult() {
        // In a real application, you would check the database connection
        // For now, we'll just return a healthy status
        return Mono.just(
            HealthResult.builder("database")
                .status(HealthStatus.UP)
                .details(Map.of("status", "connected"))
                .build()
        );
    }
}
