package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        loginPage = new LoginPage(getDriver());
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(getDriver());
    }

    @Test
    public void addProductToCartTest() {
        inventoryPage.addFirstProductToCart();

        Assert.assertTrue(inventoryPage.isCartBadgeDisplayed(),
                "Cart badge not displayed after adding product!");
        Assert.assertEquals(inventoryPage.getCartCount(), "1",
                "Cart count should be 1!");
    }

    @Test
    public void verifyCartCountTest() {
        inventoryPage.addProductToCartByIndex(0);
        inventoryPage.addProductToCartByIndex(1);
        inventoryPage.addProductToCartByIndex(2);

        Assert.assertEquals(inventoryPage.getCartCount(), "3",
                "Cart count should be 3 after adding 3 products!");
    }

    @Test
    public void removeProductFromCartTest() {
        inventoryPage.addFirstProductToCart();
        Assert.assertEquals(inventoryPage.getCartCount(), "1",
                "Cart count should be 1 after adding!");

        inventoryPage.addProductToCartByIndex(0);
        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed(),
                "Cart badge should disappear after removing product!");
    }
}
