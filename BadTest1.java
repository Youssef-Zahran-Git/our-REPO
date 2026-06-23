package Tests;

import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BadTest1 extends BaseTest {

    @Test(description = "Verify Login Bug: System bypasses authentication with invalid password")
    public void testLoginWithInvalidPasswordBug() {
        System.out.println("--- Starting Negative Test: Login Bug ---");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("twoorder@sweetshop.local")
                .enterPassword("1234");

        loginPage.clickOnLoginButton();

        boolean isStillOnLoginPage = driver.getCurrentUrl().contains("login");

        Assert.assertTrue(isStillOnLoginPage, "🔴 BUG REPRODUCED: System allowed access with an invalid password! Security flaw detected.");
    }
}