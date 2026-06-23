package Tests;

import Pages.BasketPage;
import Pages.HomePage;
import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HappyTest extends BaseTest {

    @Test(description = "End-to-End Happy Path: Login -> Add Specific Sweets -> Checkout")
    public void testCompleteShoppingFlow() {

        // 1. تهيئة الصفحات
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        BasketPage basketPage = new BasketPage(driver);

        // 2. تسجيل الدخول مباشرة (لأن الـ URL بيبدأ من صفحة الـ Login)
        loginPage.enterEmail("oneorder@sweetshop.local")
                .enterPassword("qwerty");
        loginPage.clickOnLoginButton();

        // 3. الذهاب لصفحة الـ Sweets (من الهيدر لو كان الموقع بيحولك لصفحة الحساب)
        // لو الهيدر عندك في الـ HomePage تقدر تستدعيه مباشرة
        homePage.clickOnBasketIcon(); // خطوة مؤقتة أو ننتقل للـ Sweets مباشرة لو اللينك متاح

        // بما إننا عاوزين نروح لصفحة الحلويات، نضمن إننا واقفين عليها:
        driver.get("https://sweetshop.netlify.app/sweets");

        // 4. إضافة المنتجين المطلوبين (السكرول بيحصل أوتوماتيك جوه الـ Function)
        homePage.addSweetToBasket("Chocolate Cups")
                .addSweetToBasket("Sherbert Straws");

        // تحقق سريع من العداد
        Assert.assertEquals(homePage.getBasketItemCount(), "2", "عدد المنتجات في السلة غير صحيح!");

        // 5. الذهاب إلى سلة المشتريات
        homePage.clickOnBasketIcon();

        // 6. ملء بيانات الدفع والتوصيل
        basketPage.fillBillingAddress(
                        "Bedo",
                        "Adel",
                        "bedoadel646@gmail.com",
                        "123 Main St",
                        "Apartment 15",
                        "United Kingdom", // الـ Country الصح
                        "Cardiff",        // الـ City الصح
                        "CF10 1BH"        // الـ Zip Code المتوافق مع بريطانيا مثلاً
                )
                .fillPaymentDetails(
                        "Abdelrahman Adel Farouk Ahmed",
                        "1234567812345678",
                        "12/28",
                        "123"
                );

        // 7. إتمام الطلب
        basketPage.clickOnContinueToCheckout();

        // 8. التحقق النهائي (بإضافة الانتظار الذكي - Explicit Wait)
        // الانتظار لمدة تصل إلى 10 ثواني حتى يتغير الرابط ليحتوي على الكلمة المتوقعة
// 8. التحقق النهائي (بإضافة الانتظار الذكي - Explicit Wait)
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        // غيرنا الكلمة هنا عشان تطابق اللينك الحقيقي اللي الموقع بيقف عليه
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("basket?"));

        // الـ Assert النهائي
        Assert.assertTrue(driver.getCurrentUrl().contains("basket?"), "لم يتم إتمام الطلب أو الفورم مبعتتش الداتا!");

    }
}