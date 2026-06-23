package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasketPage {

    final WebDriver driver;

    // --- Locators ---
    By firstNameInput = By.xpath("//label[contains(text(), 'First name')]/following::input[1]");
    By lastNameInput = By.xpath("//label[contains(text(), 'Last name')]/following::input[1]");
    By emailInput = By.xpath("//label[contains(text(), 'Email')]/following::input[1]");
    By addressInput = By.id("address");
    By address2Input = By.id("address2");
    By countryDropdown = By.id("country");
    By cityDropdown = By.id("city");
    By zipInput = By.id("zip");

    // بيانات الدفع
    By nameOnCardInput = By.id("cc-name");
    By creditCardNumberInput = By.id("cc-number");
    By expirationInput = By.id("cc-expiration");
    By cvvInput = By.id("cc-cvv");

    By continueToCheckoutBtn = By.xpath("//button[contains(text(), 'Continue to checkout')]");

    public BasketPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Methods ---
    public BasketPage fillBillingAddress(String fName, String lName, String email, String address, String address2, String country, String city, String zip) {
        Utility.sendKey(firstNameInput, fName, driver);
        Utility.sendKey(lastNameInput, lName, driver);
        Utility.sendKey(emailInput, email, driver);
        Utility.sendKey(addressInput, address, driver);
        Utility.sendKey(address2Input, address2, driver);

        // التعديل هنا: استخدام ميثود الـ Dropdown الجديدة
        Utility.selectFromDropdown(countryDropdown, country, driver);
        Utility.selectFromDropdown(cityDropdown, city, driver);

        Utility.sendKey(zipInput, zip, driver);
        return this;
    }

    public BasketPage fillPaymentDetails(String nameOnCard, String cardNumber, String expiration, String cvv) {
        Utility.sendKey(nameOnCardInput, nameOnCard, driver);
        Utility.sendKey(creditCardNumberInput, cardNumber, driver);
        Utility.sendKey(expirationInput, expiration, driver);
        Utility.sendKey(cvvInput, cvv, driver);
        return this;
    }

    public void clickOnContinueToCheckout() {
        Utility.click(continueToCheckoutBtn, driver);
    }
}