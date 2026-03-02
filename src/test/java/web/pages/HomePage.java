package web.pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By loginBtn = By.id("login2");
    private final By signUpBtn = By.id("signin2");
    private final By logoutBtn = By.id("logout2");
    private final By cartBtn = By.id("cartur");
    private final By loggedUser = By.id("nameofuser");
    private final By signUpUsernameField = By.id("sign-username");
    private final By signUpModal = By.id("signInModal");


    public void clickLogin() {
        // Defensive wait, so as not to be covered by the modal
        try {
            waitHelper.waitForInvisibility(signUpModal);
        } catch (Exception ignored) {}
        waitHelper.waitForClickable(loginBtn).click();
    }

    public void clickSignUp() {
        waitHelper.waitForClickable(signUpBtn).click();
        waitHelper.waitForVisibility(signUpUsernameField); // Make sure the username field is ready to be clicked
    }

    public void clickLogout() {
        // Wait for the logout button to appear
        waitHelper.waitForVisibility(logoutBtn);
        try {
            // Try normal click first
            waitHelper.waitForClickable(logoutBtn).click();
        } catch (Exception e) {
            // ===== FINAL FALLBACK =====
            // If it still intercepts → use JavaScript click
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();",
                            driver.findElement(logoutBtn));
        }
    }

    public void clickCart() {
        waitHelper.waitForClickable(cartBtn).click();
    }

    public boolean isLogoutVisible() {
        return waitHelper.waitForVisibility(logoutBtn).isDisplayed();
    }

    public String getLoggedInUsername() {
        return waitHelper.waitForVisibility(loggedUser).getText();
    }

    public boolean isLoginVisible() {
        return waitHelper.waitForVisibility(loginBtn).isDisplayed();
    }

    public boolean isLogoutVisibleSafe() {
        try {
            return driver.findElement(logoutBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}