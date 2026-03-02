package web.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import web.utils.ConfigReader;
import web.utils.DriverFactory;
import java.time.Duration;

public class WebHooks {

    private WebDriver driver;

    @Before
    public void beforeAllTests() {
        System.out.println("=== Starting WEB scenario ===");

        // Initialize driver
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();

        // Set implicit wait from config
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(
                        ConfigReader.getIntProperty("implicitWait")
                )
        );

        driver.manage().window().maximize();

        // Open base URL from config
        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    @After
    public void afterAllTests(Scenario scenario) {
        System.out.println("=== Ending WEB scenario ===");

        // Take screenshot if scenario fails
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(
                    screenshot,"image/png", scenario.getName()
            );
        }

        DriverFactory.quitDriver();
    }
}