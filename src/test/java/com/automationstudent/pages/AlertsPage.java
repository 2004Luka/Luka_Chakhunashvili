package com.automationstudent.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By alertWithTextboxTab = By.xpath("//a[contains(text(),'Alert with Textbox')]");
    private final By promptButton = By.xpath("//button[contains(., 'prompt')]");
    private final By promptResultMessage = By.id("demo1");

    public AlertsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

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

    @Step("Type into prompt alert and accept")
    public AlertsPage fillPromptAndAccept(String name) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(name);
        alert.accept();
        return this;
    }

    @Step("Read prompt result text")
    public String getPromptResultMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(promptResultMessage)).getText().trim();
    }
}