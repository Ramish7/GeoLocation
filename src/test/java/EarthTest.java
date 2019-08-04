import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
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
public class EarthTest {
    private final String PAGE_ADDRESS = "earth.html";
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
    public void NaNLatitude() {
        // Arrange
        webDriver.get(Config.ADDRESS + PAGE_ADDRESS);
        WebElement latitudeInput = webDriver.findElement(By.cssSelector("#latitude"));
        WebElement longitudeInput = webDriver.findElement(By.cssSelector("#longitude"));
        WebElement findButton = webDriver.findElement(By.cssSelector("#findButton"));
        WebElement notification = webDriver.findElement(By.cssSelector("#notification"));

        // Act
        latitudeInput.clear();
        longitudeInput.sendKeys("35");
        findButton.click();

        // Assert
        assertSame(true, notification.isDisplayed());
        assertEquals("Please enter a number as latitude", notification.getText());
    }

    @Test
    public void NaNLongitude() {
        // Arrange
        webDriver.get(Config.ADDRESS + PAGE_ADDRESS);
        WebElement latitudeInput = webDriver.findElement(By.cssSelector("#latitude"));
        WebElement longitudeInput = webDriver.findElement(By.cssSelector("#longitude"));
        WebElement findButton = webDriver.findElement(By.cssSelector("#findButton"));
        WebElement notification = webDriver.findElement(By.cssSelector("#notification"));

        // Act
        longitudeInput.sendKeys("string");
        latitudeInput.sendKeys("35");
        findButton.click();

        // Assert
        assertSame(true, notification.isDisplayed());
        assertEquals("Please enter a number as longitude", notification.getText());
    }

    @Test
    public void invalidLatitude() {
        // Arrange
        webDriver.get(Config.ADDRESS + PAGE_ADDRESS);
        WebElement latitudeInput = webDriver.findElement(By.cssSelector("#latitude"));
        WebElement longitudeInput = webDriver.findElement(By.cssSelector("#longitude"));
        WebElement findButton = webDriver.findElement(By.cssSelector("#findButton"));
        WebElement notification = webDriver.findElement(By.cssSelector("#notification"));

        // Act
        latitudeInput.clear();
        latitudeInput.sendKeys("91");
        longitudeInput.sendKeys("35");
        findButton.click();

        // Assert
        assertSame(true, notification.isDisplayed());
        assertEquals("Invalid latitude", notification.getText());
    }

    @Test
    public void invalidLongitude() {
        // Arrange
        webDriver.get(Config.ADDRESS + PAGE_ADDRESS);
        WebElement latitudeInput = webDriver.findElement(By.cssSelector("#latitude"));
        WebElement longitudeInput = webDriver.findElement(By.cssSelector("#longitude"));
        WebElement findButton = webDriver.findElement(By.cssSelector("#findButton"));
        WebElement notification = webDriver.findElement(By.cssSelector("#notification"));

        // Act
        latitudeInput.clear();
        latitudeInput.sendKeys("35");
        longitudeInput.sendKeys("-181");
        findButton.click();

        // Assert
        assertSame(true, notification.isDisplayed());
        assertEquals("Invalid longitude", notification.getText());
    }

    @Test
    public void validInputs() {
        // Arrange
        webDriver.get(Config.ADDRESS + PAGE_ADDRESS);
        WebElement latitudeInput = webDriver.findElement(By.cssSelector("#latitude"));
        WebElement longitudeInput = webDriver.findElement(By.cssSelector("#longitude"));
        WebElement findButton = webDriver.findElement(By.cssSelector("#findButton"));
        WebElement notification = webDriver.findElement(By.cssSelector("#notification"));
        WebElement distance = webDriver.findElement(By.cssSelector("#distance"));

        // Act
        latitudeInput.clear();
        latitudeInput.sendKeys("39.925533");
        longitudeInput.sendKeys("32.866287");
        findButton.click();

        // Assert
        assertSame(false, notification.isDisplayed());
        assertDoesNotThrow(() -> {
            WebDriverWait wait = new WebDriverWait(webDriver, 10);
            wait.until(ExpectedConditions.visibilityOf(distance));
        });
        assertSame(true, distance.isDisplayed());
        assertEquals("", notification.getText());
        assertEquals(6370246, Double.parseDouble(distance.getText()), 500.0d);
    }
}
