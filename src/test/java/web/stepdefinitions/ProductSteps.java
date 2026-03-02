package web.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import web.pages.HomePage;
import web.pages.ProductPage;
import web.pages.CartPage;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductSteps {

    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();
    private final HomePage homePage = new HomePage();

    // =========================
    // CATEGORY
    // =========================
    @When("the user selects the {string} category")
    public void theUserSelectsTheCategory(String category) {
        productPage.selectCategory(category);
    }

    @Then("products under {string} should be displayed")
    public void productsUnderCategoryShouldBeDisplayed(String category) {
        assertTrue(productPage.isProductsDisplayed(),
                "No products displayed under category: " + category
        );
    }

    // =========================
    // PRODUCT DETAIL
    // =========================
    @When("the user selects product {string}")
    public void selectProduct(String productName) {
        productPage.selectProduct(productName);
    }

    @Then("product detail page for {string} should be displayed")
    public void productDetailPageShouldBeDisplayed(String productName) {
        assertTrue(productPage.isOnProductDetailPage(productName),
                "Not on product detail page for: " + productName
        );
    }

    // =========================
    // ADD PRODUCT TO CART
    // =========================
    @When("the user adds the product to the cart")
    public void theUserAddsTheProductToTheCart() {
        productPage.addToCart(); // alert handled inside page
    }

    @When("the user navigates to cart")
    public void userNavigatesToCart() {
        homePage.clickCart();
    }

    // =========================
    // REMOVE PRODUCT FROM CART
    // =========================
    @When("the user adds product {string} to the cart")
    public void theUserAddsProductToTheCart(String productName) {
        productPage.selectProduct(productName);
        productPage.addToCart();    // alert handled inside page, no need productPage.acceptAlert();
    }

    @And("the user opens the cart page")    // CART NAVIGATION
    public void theUserOpensTheCartPage() {
        homePage.clickCart();
        assertTrue(cartPage.isOnCartPage(), "User is not on Cart page"
        );
    }

    @When("the user removes product {string} from the cart")
    public void removeProductFromCart(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("product {string} should not be visible in the cart")
    public void productShouldNotBeVisibleInTheCart(String productName) {
        assertFalse(cartPage.isProductInCart(productName),
                "Product should NOT be present in cart: " + productName
        );
    }
}