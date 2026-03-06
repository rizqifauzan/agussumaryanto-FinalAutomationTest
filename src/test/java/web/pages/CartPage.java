package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private final By cartRows = By.xpath("//tbody[@id='tbodyid']/tr");
    private final By totalPrice = By.id("totalp");
    private final By placeOrderButton = By.xpath("//button[text()='Place Order']");
    private final By cartTitle = By.xpath("//h2[text()='Products']");
    private final By deleteButton = By.linkText("Delete");

    // =========================
    // VALIDATE CART PAGE
    // =========================
    public boolean isOnCartPage() {
        waitHelper.waitForPageLoad();
        return waitHelper.waitForVisibility(cartTitle).isDisplayed();
    }

    // =========================
    // CHECK PRODUCT IN CART
    // =========================
    public boolean isProductInCart(String productName) {
        List<WebElement> rows = driver.findElements(cartRows);
        for (WebElement row : rows) {
            WebElement productCell = row.findElement(By.xpath("./td[2]"));
            if (productCell.getText().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    // =========================
    // REMOVE PRODUCT
    // =========================
    public void removeProduct(String productName) {
        waitHelper.waitForVisibility(cartRows);
        List<WebElement> rows = driver.findElements(cartRows);
        for (WebElement row : rows) {
            WebElement productCell = row.findElement(By.xpath("./td[2]"));
            if (productCell.getText().equalsIgnoreCase(productName)) {
                row.findElement(deleteButton).click();
                // WAIT until the product is completely gone from the DOM (Document Object Model) structure
                waitHelper.waitUntil(
                        ExpectedConditions.invisibilityOfElementLocated(
                                By.xpath("//td[text()='" + productName + "']")
                        )
                );
                return;
            }
        }
        throw new RuntimeException("Product not found in cart: " + productName);
    }

    // =========================
    // TOTAL PRICE
    // =========================
    public int getTotalPrice() {
        String totalText = waitHelper.waitForVisibility(totalPrice).getText();
        return Integer.parseInt(totalText.trim());
    }

    // =========================
    // PLACE ORDER
    // =========================
    public void clickPlaceOrder() {
        // Safety guard: if there is still an alert, close it first
        try {
            waitHelper.waitForAlert().accept();
        } catch (Exception ignored) {
            // No alert → continue normally
        }
        waitHelper.waitForClickable(placeOrderButton).click();
    }
}