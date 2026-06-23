package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BadTest4 extends BaseTest {

    @Test(priority = 1)
    public void testLoginWithInvalidCredentialsBug() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterEmail("invalid@test.com")
                .enterPassword("WrongPass123")
                .clickOnLoginButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("login"),
                "Bug: The system allowed login with invalid credentials!");
    }

    @Test(priority = 2)
    public void testBasketCounterUpdateBug() {
        driver.get("https://sweetshop.netlify.app/sweets");

        HomePage homePage = new HomePage(driver);

        homePage.addSweetToBasket("Chocolate Cups");

        String currentCount = homePage.getBasketItemCount();
        Assert.assertEquals(currentCount, "1",
                "Bug: The basket counter did not update correctly after adding an item.");
    }

    @Test(priority = 3)
    public void testCheckoutWithEmptyBillingAddressBug() {
        driver.get("https://sweetshop.netlify.app/basket");

        BasketPage basketPage = new BasketPage(driver);

        basketPage.fillBillingAddress("", "", "", "", "", "United Kingdom", "Bristol", "");
        basketPage.fillPaymentDetails("Test Name", "1234567812345678", "12/26", "123");

        basketPage.clickOnContinueToCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("basket"),
                "Bug: The system allowed checkout without mandatory billing address fields!");
    }

    @Test(priority = 4)
    public void testInvalidCvvLengthValidationBug() {
        driver.get("https://sweetshop.netlify.app/basket");

        BasketPage basketPage = new BasketPage(driver);

        basketPage.fillBillingAddress("Test", "User", "test@test.com", "123 Street", "", "United Kingdom", "Bristol", "12345");
        basketPage.fillPaymentDetails("Test User", "1234567812345678", "12/25", "1"); 

        basketPage.clickOnContinueToCheckout();

        
        Assert.assertTrue(driver.getCurrentUrl().contains("basket"),
                "Bug: The system accepted an invalid CVV length (1 digit)!");
    }

    @Test(priority = 5)
    public void testCheckoutWithMissingExpirationDateBug() {
        driver.get("https://sweetshop.netlify.app/basket");

        BasketPage basketPage = new BasketPage(driver);

    
        basketPage.fillBillingAddress("Test", "User", "test@test.com", "123 Street", "", "United Kingdom", "Bristol", "12345");
        basketPage.fillPaymentDetails("Test User", "1234567812345678", "", "123");

        basketPage.clickOnContinueToCheckout();


        Assert.assertTrue(driver.getCurrentUrl().contains("basket"),
                "Bug: The system allowed checkout with a missing card expiration date!");
    }
}
