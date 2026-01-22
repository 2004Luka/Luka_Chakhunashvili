package com.automationstudent.tests;

import com.automationstudent.base.BaseTest;
import com.automationstudent.pages.PracticeFormPage;
import com.automationstudent.utils.TestData;
import com.automationstudent.utils.Urls;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("UI Automation")
@Feature("Forms")
public class FormTests extends BaseTest {

    @Test(description = "Fill practice form and verify submission modal content.")
    @Story("User can submit the practice form successfully")
    public void testFillPracticeForm() {
        PracticeFormPage formPage = new PracticeFormPage(driver, waitFor());

        formPage.open(Urls.PRACTICE_FORM)
                .typeFirstName(TestData.FIRST_NAME)
                .typeLastName(TestData.LAST_NAME)
                .typeEmail(TestData.EMAIL)
                .selectGenderMale()
                .typeMobile(TestData.MOBILE)
                .selectHobbySports()
                .typeAddress(TestData.ADDRESS)
                .selectState(TestData.STATE)
                .selectCity(TestData.CITY)
                .submit()
                .waitForResultModal();

        Assert.assertTrue(formPage.getStudentNameFromModal().contains(TestData.FULL_NAME), "Name must match");
        Assert.assertEquals(formPage.getGenderFromModal(), "Male", "Gender must be Male");
        Assert.assertEquals(formPage.getMobileFromModal(), TestData.MOBILE, "Mobile must match");
        Assert.assertTrue(formPage.getAddressFromModal().contains(TestData.ADDRESS), "Address must match");

        String stateCity = formPage.getStateAndCityFromModal();
        Assert.assertTrue(stateCity.contains(TestData.STATE) || stateCity.contains(TestData.CITY),
                "State and City should contain selected values");
    }
}