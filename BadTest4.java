package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BadTest4 extends BaseTest {

    // Bug 1: النظام يسمح بتسجيل الدخول ببيانات فارغة أو غير صحيحة
    @Test(priority = 1)
    public void testLoginWithInvalidCredentialsBug() {
        LoginPage loginPage = new LoginPage(driver);

        // محاولة تسجيل الدخول ببيانات خاطئة
        loginPage.enterEmail("invalid@test.com")
                .enterPassword("WrongPass123")
                .clickOnLoginButton();

        // التحقق من بقاء المستخدم في صفحة الـ Login أو ظهور رسالة خطأ
        // لو الـ Bug موجود والموقع عمل Redirect، الـ Assertion ده هيفشل
        Assert.assertTrue(driver.getCurrentUrl().contains("login"),
                "Bug: The system allowed login with invalid credentials!");
    }

    // Bug 2: عداد سلة المشتريات (Basket Count) لا يتم تحديثه بشكل صحيح عند الإضافة السريعة
    @Test(priority = 2)
    public void testBasketCounterUpdateBug() {
        // الانتقال لصفحة الحلويات (بما أن BaseTest يبدأ من صفحة Login)
        driver.get("https://sweetshop.netlify.app/sweets");

        HomePage homePage = new HomePage(driver);

        // إضافة منتج للسلة
        homePage.addSweetToBasket("Chocolate Cups");

        // التحقق من أن العداد زاد وأصبح 1
        String currentCount = homePage.getBasketItemCount();
        Assert.assertEquals(currentCount, "1",
                "Bug: The basket counter did not update correctly after adding an item.");
    }

    // Bug 3: إمكانية تجاوز صفحة الدفع (Checkout) مع ترك الحقول الإلزامية في العنوان فارغة
    @Test(priority = 3)
    public void testCheckoutWithEmptyBillingAddressBug() {
        driver.get("https://sweetshop.netlify.app/basket");

        BasketPage basketPage = new BasketPage(driver);

        // إدخال بيانات الدفع فقط وترك بيانات العنوان فارغة
        basketPage.fillBillingAddress("", "", "", "", "", "United Kingdom", "Bristol", "");
        basketPage.fillPaymentDetails("Test Name", "1234567812345678", "12/26", "123");

        basketPage.clickOnContinueToCheckout();

        // التحقق من عدم القدرة على تجاوز الصفحة (يجب أن يبقى في صفحة السلة بسبب الـ Validation)
        Assert.assertTrue(driver.getCurrentUrl().contains("basket"),
                "Bug: The system allowed checkout without mandatory billing address fields!");
    }

    // Bug 4: قبول رقم CVV للبطاقة الائتمانية غير منطقي (مثلاً رقم واحد فقط بدلاً من 3 أو 4 أرقام)
    @Test(priority = 4)
    public void testInvalidCvvLengthValidationBug() {
        driver.get("https://sweetshop.netlify.app/basket");

        BasketPage basketPage = new BasketPage(driver);

        // ملء البيانات وعمل CVV من رقم واحد فقط
        basketPage.fillBillingAddress("Test", "User", "test@test.com", "123 Street", "", "United Kingdom", "Bristol", "12345");
        basketPage.fillPaymentDetails("Test User", "1234567812345678", "12/25", "1"); // CVV غير صالح

        basketPage.clickOnContinueToCheckout();

        // التحقق من تفعيل الـ Validation وعدم معالجة الطلب
        Assert.assertTrue(driver.getCurrentUrl().contains("basket"),
                "Bug: The system accepted an invalid CVV length (1 digit)!");
    }

    // Bug 5: إمكانية الدفع بدون إدخال تاريخ انتهاء صلاحية البطاقة (Expiration Date)
    @Test(priority = 5)
    public void testCheckoutWithMissingExpirationDateBug() {
        driver.get("https://sweetshop.netlify.app/basket");

        BasketPage basketPage = new BasketPage(driver);

        // ترك حقل تاريخ الانتهاء فارغاً
        basketPage.fillBillingAddress("Test", "User", "test@test.com", "123 Street", "", "United Kingdom", "Bristol", "12345");
        basketPage.fillPaymentDetails("Test User", "1234567812345678", "", "123");

        basketPage.clickOnContinueToCheckout();

        // التحقق من بقاء المستخدم وتفعيل رسالة خطأ
        Assert.assertTrue(driver.getCurrentUrl().contains("basket"),
                "Bug: The system allowed checkout with a missing card expiration date!");
    }
}