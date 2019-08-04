import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = server.Main.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MainControllerTest {

    @Test
    public void home() {
        get("/api").then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("version", equalTo("0.1.0"))
                .body("timestamp", greaterThan(0L));
    }
}
