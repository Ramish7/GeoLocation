import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.Config;
import server.Main;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@ContextConfiguration(classes = server.Main.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IndexTest {
    private WebDriver webDriver;

    @BeforeClass
    public static void startServer() {
        Main.main(new String[0]);
    }

    @Before
    public void setUp() {
        if (webDriver == null) {
            System.setProperty("webdriver.chrome.driver", Config.CHROME_DRIVER);
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }

    @Test
    public void home() {
        // Arrange
        webDriver.get(Config.ADDRESS);
        String title = webDriver.getTitle();

        // Act

        // Assert
        assertEquals("Welcome", title);
    }
}
