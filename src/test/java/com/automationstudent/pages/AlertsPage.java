package com.automationstudent.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * "pages" layer:
 * - Stores locators (@FindBy)
 * - Exposes user actions as methods (click/type/read)
 * Tests should not use locators or WebDriver calls directly.
 */
public class AlertsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public AlertsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Alert with Textbox')]")
    private WebElement alertWithTextboxTab;

    @FindBy(xpath = "//button[contains(., 'prompt')]")
    private WebElement promptButton;

    @FindBy(id = "demo1")
    private WebElement promptResultMessage;

    @Step("Open Alerts page: {url}")
    public AlertsPage open(String url) {
        driver.get(url);
        return this;
    }

    @Step("Open 'Alert with Textbox' tab")
    public AlertsPage openAlertWithTextboxTab() {
        wait.until(ExpectedConditions.elementToBeClickable(alertWithTextboxTab)).click();
        return this;
    }

    @Step("Click Prompt button")
    public AlertsPage clickPromptButton() {
        wait.until(ExpectedConditions.elementToBeClickable(promptButton)).click();
        return this;
    }

    @Step("Type into prompt alert: {name} and accept")
    public AlertsPage fillPromptAndAccept(String name) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(name);
        alert.accept();
        return this;
    }

    @Step("Read prompt result text")
    public String getPromptResultMessage() {
        return wait.until(ExpectedConditions.visibilityOf(promptResultMessage)).getText().trim();
    }
}