package com.automationstudent.tests;

import com.automationstudent.base.BaseTest;
import com.automationstudent.pages.PracticeFormPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests call Page methods only.
 * This separation is the core benefit of POM: maintainability + readability.
 */
@Epic("UI Automation")
@Feature("Forms")
public class FormTests extends BaseTest {

    @Test(description = "Fill practice form and verify submission modal content.")
    @Story("User can submit the practice form successfully")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Fills required fields, selects gender/hobby, selects state/city,
            submits the form, and validates the confirmation modal values.
            """)
    public void testFillPracticeForm() {
        PracticeFormPage formPage = new PracticeFormPage(driver, waitFor());

        formPage.open("https://demoqa.com/automation-practice-form")
                .typeFirstName("John")
                .typeLastName("Doe")
                .typeEmail("john.doe@example.com")
                .selectGenderMale()
                .typeMobile("0712345678")
                .selectHobbySports()
                .typeAddress("123 Automation Street")
                .selectState("NCR")
                .selectCity("Delhi")
                .submit()
                .waitForResultModal();

        Assert.assertTrue(formPage.getStudentNameFromModal().contains("John Doe"), "Name must match");
        Assert.assertEquals(formPage.getGenderFromModal(), "Male", "Gender must be Male");
        Assert.assertEquals(formPage.getMobileFromModal(), "0712345678", "Mobile must match");
        Assert.assertTrue(formPage.getAddressFromModal().contains("123 Automation Street"), "Address must match");

        String stateCity = formPage.getStateAndCityFromModal();
        Assert.assertTrue(stateCity.contains("NCR") || stateCity.contains("Delhi"),
                "State and City should contain selected values");
    }
}