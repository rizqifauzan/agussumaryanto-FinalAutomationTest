package web.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;
import web.pages.CartPage;
import web.pages.CheckoutPage;
import java.util.Map;

public class CheckoutSteps {

    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();

    @When("the user clicks place order")
    public void userClicksPlaceOrder() {
        cartPage.clickPlaceOrder();
    }

    @When("the user completes checkout with:")
    public void completeCheckout(DataTable table) {

        // Validate cart total before checkout
        int total = cartPage.getTotalPrice();
        assertTrue(total > 0, "Cart total should be greater than 0");

        Map<String, String> data = table.asMap(String.class, String.class);
        checkoutPage.fillCheckoutForm(data);
    }

    @And("the user clicks the {string} button to pay")
    public void userClicksPurchaseButton(String button) {
        if (button.equalsIgnoreCase("Purchase")) {
            checkoutPage.clickPurchase();
        }
    }

    @Then("a success message {string} should be displayed")
    public void aSuccessMessageShouldBeDisplayed(String expectedMessage) {
        String actualMessage = checkoutPage.getSuccessMessage();
        assertEquals(expectedMessage, actualMessage, "Success message mismatch");

    }

    @When("the user confirms purchase")
    public void userConfirmsPurchase() {
        checkoutPage.confirmPurchase();
    }

    @When("the user completes checkout with empty required fields")
    public void userCompletesCheckoutWithEmptyRequiredFields() {
        checkoutPage.submitEmptyCheckout();
    }
}