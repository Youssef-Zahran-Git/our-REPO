package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    final WebDriver driver;

    // Locators
    By emailInput = By.id("exampleInputEmail");
    By passwordInput = By.id("exampleInputPassword");
    By loginButton = By.xpath("//button[@type='submit']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public LoginPage enterEmail(String email) {
        Utility.sendKey(emailInput, email, driver);
        return this; // Return this to support fluent chaining
    }

    public LoginPage enterPassword(String password) {
        Utility.sendKey(passwordInput, password, driver);
        return this; // Return this to support fluent chaining
    }

    public void clickOnLoginButton() {
        Utility.click(loginButton, driver);
    }
}