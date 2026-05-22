package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LogoutAndSortTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        loginPage = new LoginPage(getDriver());
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(getDriver());
    }

    @Test
    public void logoutTest() {
        inventoryPage.logout();

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.equals("https://www.saucedemo.com/"),
                "Logout failed! Not redirected to login page.");
    }

    @Test
    public void sortProductsLowToHighTest() {
        inventoryPage.selectSortOption("lohi");

        List<Double> prices = inventoryPage.getAllProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "Prices not sorted low to high!");
        }
    }

    @Test
    public void sortProductsHighToLowTest() {
        inventoryPage.selectSortOption("hilo");

        List<Double> prices = inventoryPage.getAllProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) >= prices.get(i + 1),
                    "Prices not sorted high to low!");
        }
    }

    @Test
    public void sortProductsAtoZTest() {
        inventoryPage.selectSortOption("az");

        List<String> names = inventoryPage.getAllProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(names.get(i).compareTo(names.get(i + 1)) <= 0,
                    "Names not sorted A-Z!");
        }
    }
}
