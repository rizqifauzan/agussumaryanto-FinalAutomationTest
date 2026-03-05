package web.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.pages.CartPage;
import web.pages.HomePage;
import web.pages.LoginPage;
import web.utils.DriverFactory;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {

    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final CartPage cartPage = new CartPage();

    // get driver via getter, don't access private fields
    WebDriver driver = DriverFactory.getDriver();

    @Given("the user is on the homepage")
    public void userIsOnHomepage() {
        // URL already opened in WebHooks
    }

    @When("the user clicks the {string} button")
    public void userClicksButton(String button) {
        switch (button.toLowerCase()) {
            case "login":
                // Wait for login capital to be ready + alert handled
                try {
                    WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(2));
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                    alert.accept(); // Handle alerts that may arise from previous tests
                } catch (Exception ignored) {
                    // no alert
                }
                homePage.clickLogin(); // Waiting for login field to become visible
                break;
            case "sign up":
                homePage.clickSignUp();
                break;
            case "logout":
                homePage.clickLogout();
                break;
            case "cart":
                homePage.clickCart();
                break;
            case "place order":
                cartPage.clickPlaceOrder();
                break;
            default:
                throw new RuntimeException("Button not supported: " + button);
        }
    }

    @Then("the logout button should be visible")
    public void logoutButtonShouldBeVisible() {
        assertTrue(homePage.isLogoutVisible(), "Logout button should be visible");
    }

    @Then("the username {string} should be visible in the navbar")
    public void theUsernameShouldBeVisibleInTheNavbar(String username) {
        // Wait until the username appears in the navbar
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        String actual = wait.until(driver -> homePage.getLoggedInUsername());
        assertTrue(actual.contains(username),
                "Expected username not found in navbar. Actual: " + actual
        );
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        // Login with registered account
        homePage.clickLogin();
        loginPage.login("agussumaryanto", "agussumaryanto");
        // Optional: assertion to ensure login success
        homePage.getLoggedInUsername();
        System.out.println("User logged in with successfully");
    }

    @When("the user clicks the logout button")
    public void theUserClicksTheLogoutButton() {
        homePage.clickLogout();
    }

    @Then("the login button should be visible")
    public void theLoginButtonShouldBeVisible() {
        assertTrue(homePage.isLoginVisible(), "Login button should be visible");
    }

    @Then("the logout button should not be visible")
    public void theLogoutButtonShouldNotBeVisible() {
        assertFalse(homePage.isLogoutVisibleSafe(),
                "Logout button should NOT be visible"
        );
    }

    @Then("the username {string} is still visible in the navbar")
    public void theUsernameIsStillVisibleIinTheNavbar(String username) {
        boolean isVisible;
        try {
            WebElement userElement = driver.findElement(By.id("nameofuser"));
            isVisible = userElement.isDisplayed() && userElement.getText().contains(username);
        } catch (Exception e) {
            isVisible = false; // element is missing → according to negative expectation
        }
        Assertions.assertFalse(isVisible, "Username is still visible in navbar");
    }
}