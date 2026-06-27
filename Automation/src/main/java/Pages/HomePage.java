package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    final WebDriver driver;

    By basketIcon = By.cssSelector("a[href='/basket']");
    By basketCountBadge = By.className("badge");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public By sweetItemAddBtn(String sweetName) {
        return By.xpath("//h4[contains(text(), '" + sweetName + "')]/ancestor::div[contains(@class, 'card')]//a[contains(text(), 'Add to Basket')]");
    }

    public HomePage addSweetToBasket(String sweetName) {
        By locator = sweetItemAddBtn(sweetName);
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
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