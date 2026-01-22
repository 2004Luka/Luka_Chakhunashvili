package com.automationstudent.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object for DemoQA Practice Form.
 * Uses Page Factory (@FindBy) and exposes actions as methods annotated with @Step,
 * so every UI interaction is visible in Allure.
 */
public class PracticeFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public PracticeFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // --- fields ---
    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "userEmail")
    private WebElement emailInput;

    @FindBy(xpath = "//label[text()='Male']")
    private WebElement genderMaleLabel;

    @FindBy(id = "userNumber")
    private WebElement mobileInput;

    @FindBy(xpath = "//label[text()='Sports']")
    private WebElement hobbySportsLabel;

    // Clicking the input directly is often more reliable than clicking the label when overlays exist.
    @FindBy(id = "hobbies-checkbox-1")
    private WebElement hobbySportsCheckboxInput;

    @FindBy(id = "currentAddress")
    private WebElement addressInput;

    // React dropdowns
    @FindBy(id = "state")
    private WebElement stateContainer;

    @FindBy(css = "#state input")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityContainer;

    @FindBy(css = "#city input")
    private WebElement cityInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    // --- modal ---
    @FindBy(css = ".modal-content")
    private WebElement resultModal;

    @FindBy(xpath = "//td[text()='Student Name']/following-sibling::td")
    private WebElement modalStudentName;

    @FindBy(xpath = "//td[text()='Gender']/following-sibling::td")
    private WebElement modalGender;

    @FindBy(xpath = "//td[text()='Mobile']/following-sibling::td")
    private WebElement modalMobile;

    @FindBy(xpath = "//td[text()='Address']/following-sibling::td")
    private WebElement modalAddress;

    @FindBy(xpath = "//td[text()='State and City']/following-sibling::td")
    private WebElement modalStateAndCity;

    @Step("Open Practice Form page: {url}")
    public PracticeFormPage open(String url) {
        driver.get(url);
        return this;
    }

    @Step("Type First Name: {firstName}")
    public PracticeFormPage typeFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
        return this;
    }

    @Step("Type Last Name: {lastName}")
    public PracticeFormPage typeLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
        return this;
    }

    @Step("Type Email: {email}")
    public PracticeFormPage typeEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Select Gender: Male")
    public PracticeFormPage selectGenderMale() {
        wait.until(ExpectedConditions.elementToBeClickable(genderMaleLabel)).click();
        return this;
    }

    @Step("Type Mobile: {mobile}")
    public PracticeFormPage typeMobile(String mobile) {
        mobileInput.sendKeys(mobile);
        return this;
    }

    @Step("Select Hobby: Sports")
    public PracticeFormPage selectHobbySports() {
        // Ads/iframes can block clicks on DemoQA. This makes the click resilient.
        hideBlockingAdsIfPresent();
        safeClick(hobbySportsLabel, hobbySportsCheckboxInput);
        return this;
    }

    @Step("Type Address: {address}")
    public PracticeFormPage typeAddress(String address) {
        addressInput.sendKeys(address);
        return this;
    }

    @Step("Select State: {state}")
    public PracticeFormPage selectState(String state) {
        wait.until(ExpectedConditions.elementToBeClickable(stateContainer)).click();
        wait.until(ExpectedConditions.elementToBeClickable(stateInput)).sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);
        return this;
    }

    @Step("Select City: {city}")
    public PracticeFormPage selectCity(String city) {
        wait.until(ExpectedConditions.elementToBeClickable(cityContainer)).click();
        wait.until(ExpectedConditions.elementToBeClickable(cityInput)).sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
        return this;
    }

    @Step("Submit the form")
    public PracticeFormPage submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        return this;
    }

    @Step("Wait for submission modal")
    public PracticeFormPage waitForResultModal() {
        wait.until(ExpectedConditions.visibilityOf(resultModal));
        return this;
    }

    @Step("Read modal: Student Name")
    public String getStudentNameFromModal() {
        return wait.until(ExpectedConditions.visibilityOf(modalStudentName)).getText().trim();
    }

    @Step("Read modal: Gender")
    public String getGenderFromModal() {
        return modalGender.getText().trim();
    }

    @Step("Read modal: Mobile")
    public String getMobileFromModal() {
        return modalMobile.getText().trim();
    }

    @Step("Read modal: Address")
    public String getAddressFromModal() {
        return modalAddress.getText().trim();
    }

    @Step("Read modal: State and City")
    public String getStateAndCityFromModal() {
        return modalStateAndCity.getText().trim();
    }

    /**
     * Tries to click normally; if an overlay intercepts the click, falls back to JavaScript click.
     * We keep this in the Page Object so tests stay clean (no driver logic in tests).
     */
    private void safeClick(WebElement preferredElement, WebElement fallbackElement) {
        WebElement toClick = (preferredElement != null) ? preferredElement : fallbackElement;

        wait.until(ExpectedConditions.visibilityOf(toClick));
        scrollIntoView(toClick);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(toClick)).click();
        } catch (ElementClickInterceptedException e) {
            // If something overlays the element, JS click ignores the physical click constraints.
            jsClick((fallbackElement != null) ? fallbackElement : toClick);
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

    /**
     * DemoQA sometimes shows a sticky/anchor ad iframe that intercepts clicks.
     * Hiding it is a pragmatic approach for demo sites (not recommended for real apps unless agreed).
     */
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