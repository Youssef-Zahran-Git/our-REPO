package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HappyTest extends BaseTest {

    @Test
    public void testCompleteShoppingFlow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("oneorder@sweetshop.local")
                .enterPassword("qwerty");
        loginPage.clickOnLoginButton();

        HomePage homePage = new HomePage(driver);
        homePage.addSweetToBasket("Chocolate")
                .addSweetToBasket("Ice Cream");

        Assert.assertEquals(homePage.getBasketItemCount(), "2", "عدد العناصر في السلة مش مظبوط!");

        homePage.clickOnBasketIcon();

        BasketPage basketPage = new BasketPage(driver);
        basketPage.fillBillingAddress("Sobhey", "Dev", "sobhey@example.com", "123 Main St", "Apt 4", "United Kingdom", "London", "12345")
                .fillPaymentDetails("Sobhey Dev", "1234567890123456", "12/28", "123");

        basketPage.clickOnContinueToCheckout();
    }
}