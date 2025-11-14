package com.automationstudent.tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AlertTests extends BaseTest {

    @Test
    public void testPromptAlert() {
        driver.get("https://demo.automationtesting.in/Alerts.html");

        WebElement promptTab = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Alert with Textbox')]"))
        );
        promptTab.click();

        WebElement promptButton = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'prompt')]"))
        );
        promptButton.click();

        Alert alert = getWait().until(ExpectedConditions.alertIsPresent());
        String name = "Luka Chakhunashvili";
        alert.sendKeys(name);
        alert.accept();

        WebElement result = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("demo1"))
        );

        String expected = "Hello " + name + " How are you today";
        String actual = result.getText().trim();
        Assert.assertEquals(actual, expected, "Alert response message must match exactly.");
    }
}
