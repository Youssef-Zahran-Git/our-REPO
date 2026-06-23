package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage {

    final WebDriver driver;

    // Locators for Top Navigation Bar
    By loginMenuLink = By.cssSelector("a[href='/login']");
    By sweetsMenuLink = By.cssSelector("a[href='/sweets']");
    By browseSweetsBtn = By.xpath("//a[contains(text(), 'Browse Sweets')]"); // زرار الشاشة الرئيسية لو احتجناه

    // Constructor
    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods
    public void clickOnLoginMenu() {
        Utility.click(loginMenuLink, driver);
    }

    public void clickOnSweetsMenu() {
        Utility.click(sweetsMenuLink, driver);
    }

    public void clickOnBrowseSweetsButton() {
        Utility.click(browseSweetsBtn, driver);
    }
}