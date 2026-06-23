package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    final WebDriver driver;

    // Locators
    By basketIcon = By.cssSelector("a[href='/basket']");
    By basketCountBadge = By.className("badge");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // الـ Locator المحدث: بيطلع للـ Card الأساسي وبعدين يدور على الزرار
    public By sweetItemAddBtn(String sweetName) {
        return By.xpath("//h4[contains(text(), '" + sweetName + "')]/ancestor::div[contains(@class, 'card')]//a[contains(text(), 'Add to Basket')]");
    }

    // Methods
    public HomePage addSweetToBasket(String sweetName) {
        By locator = sweetItemAddBtn(sweetName);

        // Clean Code Scroll: السكرول الذكي بيجيب العنصر في نص الشاشة بالظبط قبل الضغط
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

        // تنفيذ الضغط من كلاس الـ Utility بتاعك
        Utility.click(locator, driver);
        return this;
    }

    public void clickOnBasketIcon() {
        Utility.click(basketIcon, driver);
    }

    public String getBasketItemCount() {
        return Utility.getText(basketCountBadge, driver);
    }
}