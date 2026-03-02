package web.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("loginusername");
    private final By passwordField = By.id("loginpassword");
    private final By loginButton = By.xpath("//button[text()='Log in']");

    // Fill in username & password, click Login
    public void login(String username, String password) {

        waitHelper.waitForVisibility(usernameField).clear();
        waitHelper.waitForVisibility(usernameField).sendKeys(username);

        waitHelper.waitForVisibility(passwordField).clear();
        waitHelper.waitForVisibility(passwordField).sendKeys(password);

        waitHelper.waitForClickable(loginButton).click();
    }
}