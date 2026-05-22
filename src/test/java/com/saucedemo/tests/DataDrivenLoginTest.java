package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenLoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"standard_user",   "secret_sauce", "success", "inventory"},
            {"locked_out_user", "secret_sauce", "failure", "Sorry, this user has been locked out"},
            {"wrong_user",      "wrong_pass",   "failure", "Username and password do not match"},
            {"standard_user",   "",             "failure", "Password is required"},
            {"",                "secret_sauce", "failure", "Username is required"},
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password,
                          String expectedResult, String expectedText) {

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        if (expectedResult.equals("success")) {
            String currentUrl = getDriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.contains(expectedText),
                    "Login should succeed! URL: " + currentUrl);
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                    "Error should display for: " + username);
            Assert.assertTrue(loginPage.getErrorMessage().contains(expectedText),
                    "Wrong error message! Got: " + loginPage.getErrorMessage());
        }
    }
}
