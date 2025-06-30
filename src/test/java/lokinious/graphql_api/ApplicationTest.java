package lokinious.graphql_api;

import io.micronaut.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class ApplicationTest {

    @Test
    void testApplicationContextStarts() {
        try (ApplicationContext context = ApplicationContext.run()) {
            Assertions.assertTrue(context.isRunning());
        }
    }
}
