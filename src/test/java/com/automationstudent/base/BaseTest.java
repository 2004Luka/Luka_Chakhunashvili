package com.automationstudent.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

/**
 * BaseTest belongs to the "base" layer:
 * - Centralizes WebDriver setup/teardown
 * - Provides shared utilities (waits, scrolling, attachments)
 * This keeps tests clean and focused on validation.
 */
public class BaseTest {

    protected WebDriver driver;

    // Duration-based waits (modern Java style).
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
    }

    /**
     * Takes a screenshot on failure and attaches it to Allure.
     * This makes failures easier to debug from the report.
     */
    @AfterMethod(alwaysRun = true)
    public void attachScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE && driver != null) {
            attachScreenshot(driver);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected WebDriverWait waitFor() {
        return new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    protected void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
        waitFor().until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Allure @Attachment:
     * - Any returned byte[] becomes an attachment in the report.
     * - Here we attach a PNG screenshot when a test fails.
     */
    @Attachment(value = "Screenshot on failure", type = "image/png")
    protected byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}