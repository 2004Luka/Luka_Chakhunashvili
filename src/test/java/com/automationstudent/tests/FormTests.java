package com.automationstudent.tests; // package declaration

// imports
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FormTests extends BaseTest {

    /**
     * testFillPracticeForm - fills the practice form at demoqa.com and verifies the submitted modal.
     */
    @Test
    public void testFillPracticeForm() {
        // 1) Navigate to the form page
        driver.get("https://demoqa.com/automation-practice-form");

        // 2) Wait for the first name field to be present and visible
        WebElement firstName = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("firstName"))
        );
        // Fill first name
        firstName.sendKeys("John");

        // 3) Last name
        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Doe");

        // 4) Email
        WebElement email = driver.findElement(By.id("userEmail"));
        email.sendKeys("john.doe@example.com");

        // 5) Gender selection (radio) - select "Male" using a label text lookup
        // We prefer locating by label text to avoid brittle absolute XPaths.
        WebElement genderMaleLabel = driver.findElement(By.xpath("//label[text()='Male']"));
        genderMaleLabel.click();

        // 6) Mobile number
        WebElement mobile = driver.findElement(By.id("userNumber"));
        mobile.sendKeys("0712345678");

        // 7) Hobbies - choose "Sports" checkbox
        WebElement hobbySportsLabel = driver.findElement(By.xpath("//label[text()='Sports']"));
        hobbySportsLabel.click();

        // 8) Current Address
        WebElement address = driver.findElement(By.id("currentAddress"));
        address.sendKeys("123 Automation Street");

        // 9) State and City selection
        // NOTE: The demoqa form uses custom React dropdowns (not <select> tags), so Select class isn't usable.
        // However the assignment requested using Select when possible. We'll attempt to find select elements;
        // if not present, we fall back to clicking the custom dropdowns (this is the robust approach).
        try {
            // Try to locate a 'select' element for state - probably not present in this page
            List<WebElement> selects = driver.findElements(By.tagName("select"));
            if (!selects.isEmpty()) {
                // If a select exists, use the first select as an example (this block rarely runs for demoqa)
                Select selectState = new Select(selects.get(0));
                selectState.selectByVisibleText("NCR"); // example text
            } else {
                // Fallback for React custom dropdowns used on demoqa:
                // Click the state dropdown and choose "NCR"
                WebElement stateContainer = driver.findElement(By.id("state"));
                stateContainer.click();
                // Wait for the input that appears and type the option then press Enter
                WebElement stateInput = getWait().until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector("#state input"))
                );
                stateInput.sendKeys("NCR");
                stateInput.sendKeys(Keys.ENTER);

                // City selection: click city dropdown and choose "Delhi"
                WebElement cityContainer = driver.findElement(By.id("city"));
                cityContainer.click();
                WebElement cityInput = getWait().until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector("#city input"))
                );
                cityInput.sendKeys("Delhi");
                cityInput.sendKeys(Keys.ENTER);
            }
        } catch (Exception e) {
            // If anything fails while selecting state/city, fail the test with a clear message
            Assert.fail("Failed to select State/City: " + e.getMessage());
        }

        // 10) Scroll the submit button into view using BaseTest utility and click it
        WebElement submitButton = driver.findElement(By.id("submit"));
        scrollIntoView(submitButton); // ensures it's visible and centered
        submitButton.click();

        // 11) Verification: wait for the modal to appear
        WebElement modal = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-content"))
        );
        Assert.assertTrue(modal.isDisplayed(), "Submission modal should be visible");

        // 12) Verify submitted data appears in the modal table.
        // The modal contains a table where the second column holds values. We verify key fields.
        // Verify name (First and Last)
        WebElement nameCell = modal.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td"));
        Assert.assertTrue(nameCell.getText().contains("John Doe"), "Name must match");

        // Verify gender
        WebElement genderCell = modal.findElement(By.xpath("//td[text()='Gender']/following-sibling::td"));
        Assert.assertEquals(genderCell.getText(), "Male", "Gender must be Male");

        // Verify mobile
        WebElement mobileCell = modal.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td"));
        Assert.assertEquals(mobileCell.getText(), "0712345678", "Mobile must match");

        // Verify address
        WebElement addressCell = modal.findElement(By.xpath("//td[text()='Address']/following-sibling::td"));
        Assert.assertTrue(addressCell.getText().contains("123 Automation Street"), "Address must match");

        // Verify state and city concatenated cell
        WebElement stateCityCell = modal.findElement(By.xpath("//td[text()='State and City']/following-sibling::td"));
        Assert.assertTrue(stateCityCell.getText().contains("NCR") || stateCityCell.getText().contains("Delhi"),
                "State and City should contain selected values");
    }
}
