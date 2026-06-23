package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class BadTest3 extends BaseTest {

    @Test(description = "Verify Footer Bug: Social Media icons are static and non-functional")
    public void testSocialMediaLinksBug() {
        System.out.println("--- Starting Negative Test: Social Media Icons Bug ---");


        List<WebElement> socialIcons = driver.findElements(By.cssSelector("footer a.text-muted"));


        Assert.assertTrue(socialIcons.size() > 0, "No social media icons found in the footer!");


        for (WebElement icon : socialIcons) {
            String hrefValue = icon.getAttribute("href");

            System.out.println("Found Footer Link with href: " + hrefValue);


            boolean isBrokenLink = (hrefValue == null || hrefValue.trim().isEmpty() || hrefValue.endsWith("#"));

            Assert.assertFalse(isBrokenLink,
                    "🔴 BUG REPRODUCED: Social Media icon is static and non-functional! Href is empty or just a '#'.");
        }
    }
}