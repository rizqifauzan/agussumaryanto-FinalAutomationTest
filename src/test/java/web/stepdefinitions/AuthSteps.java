package web.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web.pages.LoginPage;
import web.pages.SignupPage;
import web.utils.DriverFactory;
import java.time.Duration;

public class AuthSteps {

    private final LoginPage loginPage = new LoginPage();
    private final SignupPage signUpPage = new SignupPage();

    private String currentUser; // Global variable to store the newly generated username

    // PRECONDITION
    @Given("the registration precondition for username {string}")
    public void registrationPrecondition(String username) {
        if (username == null || username.isEmpty()) {
            return;
        }
        signUpPage.createUser(username); // handle alert & close modal inside method
        currentUser = username; // Save username to login later
    }

    // SPECIAL REGISTER STEP FOR POSITIVE CASE (UNIQUE USERNAME)
    @When("the user submits registration with unique username {string} and password {string}")
    public void userRegistersUnique(String username, String password) {
        // Generate unique username & save to currentUser
        currentUser = username + System.currentTimeMillis();
        signUpPage.register(currentUser, password);
    }

    // GENERAL REGISTER STEP
    @When("the user submits registration with username {string} and password {string}")
    public void userRegisters(String username, String password) {
        currentUser = username; // Save username to login later
        signUpPage.register(username, password);
    }

    // LOGIN STEP (normal login)
    @When("the user login with username {string} and password {string}")
    public void userLogIn(String username, String password) {
        // If username or password is empty, just log, don't throw exception.
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            System.out.println("Attempting login with EMPTY username or password");
            loginPage.login(username, password); // Keep calling the login method for negative tests
            return;
        }
        // Normal login
        System.out.println("Logging in with username: " + username + " and password: " + password);
        loginPage.login(username, password);
    }

    // LOGIN STEP USING A NEWLY REGISTERED USER
    @When("the user login with the registered username and password {string}")
    public void loginWithRegisteredUsername(String password) {
        if(currentUser == null){
            throw new RuntimeException("No registered username available!"); // Safety check
        }
        System.out.println("Logging in with username: " + currentUser + " and password: " + password); // Debug log
        loginPage.login(currentUser, password);
    }

    // ALERT VERIFICATION
    @Then("an alert message {string} should be displayed")
    public void alertMessageShouldBeDisplayed(String expectedMessage) {
        // Explicit wait to ensure the alert appears
        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(5)
        );
        // Wait until the alert actually appears
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String actualMessage = alert.getText();
        assertEquals(expectedMessage, actualMessage);
        alert.accept(); // Accept after verification
    }
}