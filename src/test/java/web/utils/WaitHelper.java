package web.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitHelper {

    private final WebDriverWait wait;

    public WaitHelper(WebDriver driver) {
          this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        ConfigReader.getIntProperty("explicitWait")
                )
        );
    }

    // Wait until element is visible
    public WebElement waitForVisibility(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public List<WebElement> waitForAllVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)
        );
    }

    // Wait until element is clickable
    public WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    // Wait until element disappears
    public void waitForInvisibility(By locator) {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator)
        );
    }

    // Wait until alert is present
    public Alert waitForAlert() {
        return wait.until(
                ExpectedConditions.alertIsPresent()
        );
    }

    // GENERIC WAIT UNTIL (FLEXIBLE)
    public void waitUntil(Function<? super WebDriver, Boolean> condition) {
        wait.until(condition);
    }
}