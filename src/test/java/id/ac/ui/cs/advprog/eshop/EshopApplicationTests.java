package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring Boot application context loads successfully.
        // No further logic is required.
    }

    @Test
    void testMainMethod() {
        // This test calls the main method to improve coverage.
        EshopApplication.main(new String[]{});
    }
}
