package com.automationstudent.tests;

import com.automationstudent.base.BaseTest;
import com.automationstudent.pages.AlertsPage;
import com.automationstudent.utils.TestData;
import com.automationstudent.utils.Urls;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("UI Automation")
@Feature("Browser Alerts")
public class AlertTests extends BaseTest {

    @Test(description = "Validate prompt alert input is reflected in the result label.")
    @Story("User can type a name into a prompt alert and see confirmation text")
    public void testPromptAlert() {
        AlertsPage alertsPage = new AlertsPage(driver, waitFor());

        alertsPage.open(Urls.ALERTS)
                .openAlertWithTextboxTab()
                .clickPromptButton()
                .fillPromptAndAccept(TestData.PROMPT_NAME);

        String actual = alertsPage.getPromptResultMessage();
        String expected = "Hello " + TestData.PROMPT_NAME + " How are you today";

        Assert.assertEquals(actual, expected, "Alert response message must match exactly.");
    }
}