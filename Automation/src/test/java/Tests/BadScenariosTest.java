package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BadScenariosTest extends BaseTest {

    // 1. التيست السلبي لصفحة تسجيل الدخول (Login Page)
    @Test
    public void testLoginWithInvalidCredentials() {
        driver.get("https://sweetshop.netlify.app/login");
        LoginPage loginPage = new LoginPage(driver);

        // هنجرب ندخل إيميل وباسوورد غلط
        loginPage.enterEmail("invalid_sobhey@test.com")
                .enterPassword("WrongPassword123");
        loginPage.clickOnLoginButton();

        // التأكد: المفروض الموقع يرفض الدخول ويفضل في صفحة اللوجن
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "التست فشل: الموقع عمل لوجن ببيانات غلط!");
    }

    // 2. التيست السلبي لصفحة الهوم (Landing Page)
    @Test
    public void testLandingPageEmptyBasket() {
        driver.get("https://sweetshop.netlify.app/"); // مسار صفحة الهوم
        HomePage homePage = new HomePage(driver);

        // التأكد: هنتأكد إن عداد السلة صفر ومفيش حاجة اتضافت بالغلط من غير ما اليوزر يدوس
        String basketCount = homePage.getBasketItemCount();
        Assert.assertEquals(basketCount, "0", "التست فشل: السلة المفروض تكون فاضية في البداية!");
    }

    // 3. التيست السلبي لصفحة السلة (Basket Page)
    @Test
    public void testCheckoutWithMissingData() {
        driver.get("https://sweetshop.netlify.app/basket");
        BasketPage basketPage = new BasketPage(driver);

        // هنجرب ندوس على زرار الدفع (Checkout) من غير ما نملى بيانات الشحن الإجبارية
        basketPage.clickOnContinueToCheckout();

        // التأكد: المفروض الموقع ميكملش عملية الدفع ويفضل في نفس صفحة السلة عشان البيانات ناقصة
        Assert.assertTrue(driver.getCurrentUrl().contains("basket"), "التست فشل: الموقع كمل دفع والبيانات ناقصة!");
    }
}
