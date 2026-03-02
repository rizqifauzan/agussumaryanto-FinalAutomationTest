package web.pages;

import org.openqa.selenium.By;
import java.util.Map;

public class CheckoutPage extends BasePage {

    private final By name = By.id("name");
    private final By country = By.id("country");
    private final By city = By.id("city");
    private final By card = By.id("card");
    private final By month = By.id("month");
    private final By year = By.id("year");
    private final By purchaseBtn = By.xpath("//button[text()='Purchase']");
    private final By successMessage = By.cssSelector(".sweet-alert h2");
    private final By okButton = By.xpath("//button[text()='OK']");
    private final By successModal = By.className("sweet-alert");

    // FILL CHECKOUT FORM
    public void fillCheckoutForm(Map<String, String> data) {
        waitHelper.waitForVisibility(name).sendKeys(data.get("name"));
        waitHelper.waitForVisibility(country).sendKeys(data.get("country"));
        waitHelper.waitForVisibility(city).sendKeys(data.get("city"));
        waitHelper.waitForVisibility(card).sendKeys(data.get("credit card"));
        waitHelper.waitForVisibility(month).sendKeys(data.get("month"));
        waitHelper.waitForVisibility(year).sendKeys(data.get("year"));
    }

    // CLICK PURCHASE
    public void clickPurchase() {
        waitHelper.waitForClickable(purchaseBtn).click();
    }

    // SUCCESS MESSAGE
    public String getSuccessMessage() {
        return waitHelper.waitForVisibility(successMessage).getText();
    }

    // CONFIRM PURCHASE SUCCESS
    public void confirmPurchase() {
        waitHelper.waitForVisibility(successModal); // Make sure SweetAlert/Modal appears first
        waitHelper.waitForClickable(okButton).click(); // Click OK
        waitHelper.waitForInvisibility(successModal); // Wait for SweetAlert/Modal to completely disappear
    }

    // NEGATIVE FLOW
    public void submitEmptyCheckout() {
        // intentionally left blank
        // clickPurchase() will be called without filling in the form
    }
}