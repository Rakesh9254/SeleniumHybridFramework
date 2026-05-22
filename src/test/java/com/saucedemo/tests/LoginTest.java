package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	@Test
	public void validLoginTest() {
		log.info("Starting validLoginTest...");
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.login("standard_user", "secret_sauce");

		String currentUrl = getDriver().getCurrentUrl();
		log.info("Current URL after login: " + currentUrl);

		Assert.assertTrue(currentUrl.contains("inventory"), "Login failed! URL does not contain 'inventory'");
		log.info("validLoginTest PASSED.");
	}

	@Test
	public void invalidLoginTest() {
		log.info("Starting invalidLoginTest...");
		LoginPage loginPage = new LoginPage(getDriver());
		loginPage.login("wrong_user", "wrong_pass");

		Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed!");
		log.info("Error message: " + loginPage.getErrorMessage());
		Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
				"Unexpected error message: " + loginPage.getErrorMessage());
		log.info("invalidLoginTest PASSED.");
	}
}
