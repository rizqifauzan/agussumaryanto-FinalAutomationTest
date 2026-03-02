package web.pages;

import org.openqa.selenium.By;

public class SignupPage extends BasePage {

    // Make sure the locator matches the Signup button on the homepage
    private final By openSignUpModalButton = By.id("signin2"); // locator button to open Signup modal
    private final By usernameField = By.id("sign-username");
    private final By passwordField = By.id("sign-password");
    private final By signUpButton = By.xpath("//button[text()='Sign up']");
    private final By closeModalButton =
            By.xpath("//div[@id='signInModal']//button[text()='Close']"); // locator button to close modal

    // Method to open modal before register
    public void clickSignUpButton() {
        waitHelper.waitForClickable(openSignUpModalButton).click();
        waitHelper.waitForVisibility(usernameField); // wait modal to appear
    }

    // Isi username & password, click Signup
    public void register(String username, String password) {

        waitHelper.waitForVisibility(usernameField).clear();
        waitHelper.waitForVisibility(usernameField).sendKeys(username);

        waitHelper.waitForVisibility(passwordField).clear();
        waitHelper.waitForVisibility(passwordField).sendKeys(password);

        waitHelper.waitForClickable(signUpButton).click();
    }

    // Special method for precondition duplicate test
    public void createUser(String username) {
        clickSignUpButton();   // open modal first
        register(username, "Password123!");
        waitHelper.waitForAlert().accept();   // accept alert
        closeModal(); // close modal so that UI is clean
    }

    // Method for close modal after register
    public void closeModal() {
        waitHelper.waitForClickable(closeModalButton).click();
        waitHelper.waitForInvisibility(By.id("signInModal"));
    }

}