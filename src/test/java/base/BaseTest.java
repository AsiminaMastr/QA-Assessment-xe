package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected final String BASE_URL = "https://www.xe.gr/property";
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void logStep(String id, String message) {
        Reporter.log("[STEP " + id + "] " + message, true);
    }

    protected void logStepPass(String id) {
        Reporter.log("[STEP " + id + "][PASS]", true);
    }

    protected void logStepFail(String id, String reason) {
        Reporter.log("[STEP " + id + "][FAIL] " + reason, true);
    }
}