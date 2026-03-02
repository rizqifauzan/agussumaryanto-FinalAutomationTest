package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.logging.Logger;

public class ProductPage extends BasePage {

    private final By addToCartBtn = By.linkText("Add to cart");
    private final By productTitle = By.cssSelector(".name");
    private final By productCards = By.cssSelector(".card-title a");
    private static final Logger logger = Logger.getLogger(ProductPage.class.getName());

    // =========================
    // SELECT PRODUCT
    // =========================
    public void selectProduct(String productName) {
        By productLink = By.linkText(productName);
        int attempts = 0;
        while (attempts < 3) {
            try {
                // wait until clickable
                WebElement element = waitHelper.waitForClickable(productLink);
                element.click(); // click product
                break; // success, exit loop
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
                System.out.println("StaleElementReferenceException caught while selecting product. Retry " + attempts);
                try {
                    Thread.sleep(500); // wait a moment before retrying
                } catch (InterruptedException ie) {
                    logger.severe("Interrupted while waiting to retry: " + ie.getMessage());
                    Thread.currentThread().interrupt(); // restore interrupt status
                }
            }
        }
    }

    // =========================
    // ADD TO CART
    // =========================
    public void addToCart() {
        waitHelper.waitForClickable(addToCartBtn).click();
    }

    // =========================
    // CATEGORY
    // =========================
    public void selectCategory(String category) {
        By categoryLink = By.linkText(category);
        waitHelper.waitForClickable(categoryLink).click();
    }

    public boolean isProductsDisplayed() {
        List<WebElement> products =
                waitHelper.waitForAllVisible(productCards);
        return products != null && !products.isEmpty();
    }

    // =========================
    // PRODUCT DETAIL
    // =========================
    public boolean isOnProductDetailPage(String productName) {
        return waitHelper.waitForVisibility(productTitle)
                .getText()
                .equalsIgnoreCase(productName);
    }
}