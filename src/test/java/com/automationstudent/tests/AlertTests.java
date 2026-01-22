package com.automationstudent.tests;

import com.automationstudent.base.BaseTest;
import com.automationstudent.pages.AlertsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * "tests" layer:
 * - Contains assertions and test flow only
 * - No locators, no direct driver interaction
 */
@Epic("UI Automation")
@Feature("Browser Alerts")
public class AlertTests extends BaseTest {

    @Test(description = "Validate prompt alert input is reflected in the result label.")
    @Story("User can type a name into a prompt alert and see confirmation text")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Allure annotations:
            - @Epic: large business area grouping
            - @Feature: functional module grouping
            - @Story: specific scenario/user story
            - @Severity: business impact of failure
            - @Description: readable documentation in the report
            """)
    public void testPromptAlert() {
        AlertsPage alertsPage = new AlertsPage(driver, waitFor());

        String name = "Luka Chakhunashvili";

        alertsPage.open("https://demo.automationtesting.in/Alerts.html")
                .openAlertWithTextboxTab()
                .clickPromptButton()
                .fillPromptAndAccept(name);

        String actual = alertsPage.getPromptResultMessage();
        String expected = "Hello " + name + " How are you today";

        Assert.assertEquals(actual, expected, "Alert response message must match exactly.");
    }
}