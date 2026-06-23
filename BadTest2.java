package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LandingPage;
import Pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BadTest2 extends BaseTest {

    @Test(description = "Verify Basket Bug: CVV field accepts unlimited digits without validation")
    public void testCvvUnlimitedLengthBug() {
        System.out.println("--- Starting Negative Test: CVV Length Bug ---");


        new LoginPage(driver).enterEmail("oneorder@sweetshop.local")
                .enterPassword("qwerty")
                .clickOnLoginButton();


        new LandingPage(driver).clickOnSweetsMenu();
        new HomePage(driver).addSweetToBasket("Sherbet");


        driver.findElement(By.cssSelector("a[href='/basket']")).click();


        String invalidLongCvv = "123456789";

        BasketPage basketPage = new BasketPage(driver);
        basketPage.fillPaymentDetails("Youssef Zahran", "1234567890123456", "12/26", invalidLongCvv);


        WebElement cvvInput = driver.findElement(By.id("cc-cvv"));
        String actualCvvValue = cvvInput.getAttribute("value");

        System.out.println("Attempted to enter CVV: " + invalidLongCvv);
        System.out.println("Actual CVV accepted in field: " + actualCvvValue);


        Assert.assertTrue(actualCvvValue.length() <= 4,
                "🔴 BUG REPRODUCED: CVV field accepts " + actualCvvValue.length() + " digits (Unlimited Length) instead of max 3-4 digits!");
    }
}