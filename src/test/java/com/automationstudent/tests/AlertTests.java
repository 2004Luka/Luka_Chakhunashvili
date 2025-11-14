package com.automationstudent.tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlertTests extends BaseTest {

    /**
     * testPromptAlert - navigates to alerts demo, triggers prompt alert,
     * sends text "AutomationStudent", accepts it and verifies the page message.
     */
    @Test
    public void testPromptAlert() {
        // 1) Navigate to the target page
        driver.get("https://demo.automationtesting.in/Alerts.html");

        // 2) Click the "Alert with Textbox" tab (tab link text is used)
        WebElement promptTab = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Alert with Textbox')]"))
        );
        promptTab.click();

        // 3) Click the button that opens the prompt alert
        // On the page, the button text contains 'prompt' — use a relative XPath to find it
        WebElement promptButton = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'prompt')]"))
        );
        promptButton.click();

        // 4) Switch to alert, send keys (AutomationStudent), and accept
        Alert alert = getWait().until(ExpectedConditions.alertIsPresent());
        String name = "AutomationStudent"; // placeholder name as requested
        alert.sendKeys(name);
        alert.accept();

        // 5) Verify the resulting text on the page equals the expected string
        // The page displays a message in an element with id 'demo1' after handling the prompt.
        WebElement result = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("demo1"))
        );

        String expected = "Hello " + name + " How are you today";
        String actual = result.getText().trim();
        Assert.assertEquals(actual, expected, "Alert response message must match exactly.");
    }
}
