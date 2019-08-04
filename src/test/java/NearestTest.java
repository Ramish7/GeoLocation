import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import server.Config;
import server.Main;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = Main.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NearestTest {
    private final String PAGE_ADDRESS = "nearest.html";
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
    public void validInputs() {
        // Arrange
        webDriver.get(Config.ADDRESS + PAGE_ADDRESS);
        WebElement nearestButton = webDriver.findElement(By.cssSelector("#nearestButton"));
        WebElement city = webDriver.findElement(By.cssSelector("#city"));

        // Act
        ((JavascriptExecutor)webDriver).executeScript("latitude=39.925533;longitude=32.866287;");
        nearestButton.click();

        // Assert
        assertDoesNotThrow(() -> {
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            wait.until(ExpectedConditions.visibilityOf(city));
        });
        assertEquals("Çankaya", city.getText());
    }
}
