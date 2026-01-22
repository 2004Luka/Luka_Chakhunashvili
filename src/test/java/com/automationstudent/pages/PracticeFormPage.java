package com.automationstudent.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PracticeFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By genderMaleLabel = By.xpath("//label[text()='Male']");
    private final By mobileInput = By.id("userNumber");
    private final By hobbySportsLabel = By.xpath("//label[text()='Sports']");
    private final By addressInput = By.id("currentAddress");

    private final By stateContainer = By.id("state");
    private final By stateInput = By.cssSelector("#state input");
    private final By cityContainer = By.id("city");
    private final By cityInput = By.cssSelector("#city input");

    private final By submitButton = By.id("submit");

    private final By resultModal = By.cssSelector(".modal-content");
    private final By modalStudentName = By.xpath("//td[text()='Student Name']/following-sibling::td");
    private final By modalGender = By.xpath("//td[text()='Gender']/following-sibling::td");
    private final By modalMobile = By.xpath("//td[text()='Mobile']/following-sibling::td");
    private final By modalAddress = By.xpath("//td[text()='Address']/following-sibling::td");
    private final By modalStateAndCity = By.xpath("//td[text()='State and City']/following-sibling::td");

    public PracticeFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Open Practice Form page: {url}")
    public PracticeFormPage open(String url) {
        driver.get(url);
        return this;
    }

    @Step("Type First Name: {firstName}")
    public PracticeFormPage typeFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        return this;
    }

    @Step("Type Last Name: {lastName}")
    public PracticeFormPage typeLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
        return this;
    }

    @Step("Type Email: {email}")
    public PracticeFormPage typeEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
        return this;
    }

    @Step("Select Gender: Male")
    public PracticeFormPage selectGenderMale() {
        wait.until(ExpectedConditions.elementToBeClickable(genderMaleLabel)).click();
        return this;
    }

    @Step("Type Mobile: {mobile}")
    public PracticeFormPage typeMobile(String mobile) {
        driver.findElement(mobileInput).sendKeys(mobile);
        return this;
    }

    @Step("Select Hobby: Sports")
    public PracticeFormPage selectHobbySports() {
        clickSafely(hobbySportsLabel);
        return this;
    }

    @Step("Type Address: {address}")
    public PracticeFormPage typeAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
        return this;
    }

    @Step("Select State: {state}")
    public PracticeFormPage selectState(String state) {
        clickContainerSafely(stateContainer);
        wait.until(ExpectedConditions.elementToBeClickable(stateInput)).sendKeys(state);
        driver.findElement(stateInput).sendKeys(Keys.ENTER);
        return this;
    }

    @Step("Select City: {city}")
    public PracticeFormPage selectCity(String city) {
        clickContainerSafely(cityContainer);
        wait.until(ExpectedConditions.elementToBeClickable(cityInput)).sendKeys(city);
        driver.findElement(cityInput).sendKeys(Keys.ENTER);
        return this;
    }

    @Step("Submit the form")
    public PracticeFormPage submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        return this;
    }

    @Step("Wait for submission modal")
    public PracticeFormPage waitForResultModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultModal));
        return this;
    }

    @Step("Read modal: Student Name")
    public String getStudentNameFromModal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalStudentName)).getText().trim();
    }

    @Step("Read modal: Gender")
    public String getGenderFromModal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalGender)).getText().trim();
    }

    @Step("Read modal: Mobile")
    public String getMobileFromModal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalMobile)).getText().trim();
    }

    @Step("Read modal: Address")
    public String getAddressFromModal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalAddress)).getText().trim();
    }

    @Step("Read modal: State and City")
    public String getStateAndCityFromModal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalStateAndCity)).getText().trim();
    }

    private void clickContainerSafely(By container) {
        hideBlockingAdsIfPresent();

        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(container));
        scrollIntoView(el);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(container)).click();
        } catch (ElementClickInterceptedException e) {
            jsClick(el);
        }
    }

    private void clickSafely(By locator) {
        hideBlockingAdsIfPresent();

        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollIntoView(el);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (ElementClickInterceptedException e) {
            jsClick(el);
        }
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element
        );
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void hideBlockingAdsIfPresent() {
        ((JavascriptExecutor) driver).executeScript("""
                const iframes = Array.from(document.querySelectorAll('iframe[id^="google_ads_iframe"]'));
                iframes.forEach(f => {
                    try {
                        f.style.display = 'none';
                        f.style.visibility = 'hidden';
                        f.style.height = '0px';
                        f.style.width = '0px';
                    } catch(e) {}
                });
                """);
    }
}