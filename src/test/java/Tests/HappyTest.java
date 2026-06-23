package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HappyTest extends BaseTest {

    @Test(description = "End-to-End Happy Path: Login -> Add Specific Sweets -> Checkout")
    public void testCompleteShoppingFlow() {

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        BasketPage basketPage = new BasketPage(driver);

        loginPage.enterEmail("oneorder@sweetshop.local")
                .enterPassword("qwerty");
        loginPage.clickOnLoginButton();

        homePage.clickOnBasketIcon(); 

        driver.get("https://sweetshop.netlify.app/sweets");

        homePage.addSweetToBasket("Chocolate Cups")
                .addSweetToBasket("Sherbert Straws");

        Assert.assertEquals(homePage.getBasketItemCount(), "2", "عدد المنتجات في السلة غير صحيح!");

        homePage.clickOnBasketIcon();

        basketPage.fillBillingAddress(
                        "Bedo",
                        "Adel",
                        "bedoadel646@gmail.com",
                        "123 Main St",
                        "Apartment 15",
                        "United Kingdom", 
                        "Cardiff",       
                        "CF10 1BH"       
                )
                .fillPaymentDetails(
                        "Abdelrahman Adel Farouk Ahmed",
                        "1234567812345678",
                        "12/28",
                        "123"
                );

        basketPage.clickOnContinueToCheckout();

        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("basket?"));

        Assert.assertTrue(driver.getCurrentUrl().contains("basket?"), "لم يتم إتمام الطلب أو الفورم مبعتتش الداتا!");

    }
}
