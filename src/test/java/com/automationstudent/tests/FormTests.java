package com.automationstudent.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FormTests extends BaseTest {

    @Test
    public void testFillPracticeForm() {
        driver.get("https://demoqa.com/automation-practice-form");

        WebElement firstName = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))
        );
        firstName.sendKeys("John");

        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Doe");

        WebElement email = driver.findElement(By.id("userEmail"));
        email.sendKeys("john.doe@example.com");

        WebElement genderMaleLabel = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Male']"))
        );
        scrollIntoView(genderMaleLabel);
        genderMaleLabel.click();

        WebElement mobile = driver.findElement(By.id("userNumber"));
        mobile.sendKeys("0712345678");

        WebElement hobbySportsLabel = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Sports']"))
        );
        scrollIntoView(hobbySportsLabel);
        hobbySportsLabel.click();

        WebElement address = driver.findElement(By.id("currentAddress"));
        address.sendKeys("123 Automation Street");

        try {
            List<WebElement> selects = driver.findElements(By.tagName("select"));
            if (!selects.isEmpty()) {
                Select selectState = new Select(selects.get(0));
                selectState.selectByVisibleText("NCR");
            } else {
                WebElement stateContainer = driver.findElement(By.id("state"));
                scrollIntoView(stateContainer);
                stateContainer.click();
                WebElement stateInput = getWait().until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector("#state input"))
                );
                stateInput.sendKeys("NCR");
                stateInput.sendKeys(Keys.ENTER);

                WebElement cityContainer = driver.findElement(By.id("city"));
                scrollIntoView(cityContainer);
                cityContainer.click();
                WebElement cityInput = getWait().until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector("#city input"))
                );
                cityInput.sendKeys("Delhi");
                cityInput.sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            Assert.fail("Failed to select State/City: " + e.getMessage());
        }

        WebElement submitButton = driver.findElement(By.id("submit"));
        scrollIntoView(submitButton);
        getWait().until(ExpectedConditions.elementToBeClickable(submitButton)).click();

        WebElement modal = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-content"))
        );
        Assert.assertTrue(modal.isDisplayed(), "Submission modal should be visible");

        WebElement nameCell = modal.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td"));
        Assert.assertTrue(nameCell.getText().contains("John Doe"), "Name must match");

        WebElement genderCell = modal.findElement(By.xpath("//td[text()='Gender']/following-sibling::td"));
        Assert.assertEquals(genderCell.getText(), "Male", "Gender must be Male");

        WebElement mobileCell = modal.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td"));
        Assert.assertEquals(mobileCell.getText(), "0712345678", "Mobile must match");

        WebElement addressCell = modal.findElement(By.xpath("//td[text()='Address']/following-sibling::td"));
        Assert.assertTrue(addressCell.getText().contains("123 Automation Street"), "Address must match");

        WebElement stateCityCell = modal.findElement(By.xpath("//td[text()='State and City']/following-sibling::td"));
        Assert.assertTrue(stateCityCell.getText().contains("NCR") || stateCityCell.getText().contains("Delhi"),
                "State and City should contain selected values");
    }
}
