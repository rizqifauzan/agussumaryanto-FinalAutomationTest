package web.pages;

import org.openqa.selenium.WebDriver;
import web.utils.DriverFactory;
import web.utils.WaitHelper;

public class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("WebDriver not initialized yet!");
        }
        this.waitHelper = new WaitHelper(driver);
    }
}