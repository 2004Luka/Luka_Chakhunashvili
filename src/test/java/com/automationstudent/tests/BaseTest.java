package com.automationstudent.tests; // package - matches folder path

// imports: Selenium, TestNG, WebDriverManager and Java utilities
import io.github.bonigarcia.wdm.WebDriverManager; // manages browser driver binaries
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver; // Chrome implementation
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver; // the WebDriver instance available to subclasses
    private final Duration WAIT_TIMEOUT = Duration.ofSeconds(10); // default explicit wait

    /**
     * setUp - runs before test class methods (TestNG @BeforeClass)
     * Initializes WebDriver using WebDriverManager and basic Chrome options.
     */
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        // 1. Setup chromedriver binary automatically
        WebDriverManager.chromedriver().setup();

        // 2. Create ChromeOptions for cleaner browser startup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // start the browser maximized

        // 3. Initialize the ChromeDriver with options
        driver = new ChromeDriver(options);
    }

    /**
     * tearDown - runs after test class methods (TestNG @AfterClass)
     * Quits the browser and cleans up resources.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // close browser and end WebDriver session
        }
    }

    /**
     * getWait - helper that returns a WebDriverWait using the shared timeout
     * Use this for all explicit waits (no Thread.sleep()).
     */
    protected WebDriverWait getWait() {
        return new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    /**
     * scrollIntoView - scroll a WebElement into view using JavaScript
     * Useful for ensuring element is visible/clickable before interacting.
     *
     * @param element the WebElement to bring into view
     */
    protected void scrollIntoView(WebElement element) {
        // Cast driver to JavascriptExecutor to run JS code
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // scroll the element into view and align to center of the viewport for safety
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
        // Additionally wait until element is visible
        getWait().until(ExpectedConditions.visibilityOf(element));
    }
}
