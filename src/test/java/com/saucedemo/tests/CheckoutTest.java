package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(getDriver());
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addFirstProductToCart();
        inventoryPage.goToCart();

        cartPage = new CartPage(getDriver());
        checkoutPage = new CheckoutPage(getDriver());
    }

    @Test
    public void completeCheckoutTest() {
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item!");

        cartPage.clickCheckout();

        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "Order confirmation not displayed!");
        Assert.assertEquals(checkoutPage.getConfirmationHeader(), "Thank you for your order!",
                "Confirmation header mismatch!");
    }

    @Test
    public void verifyCartItemsBeforeCheckoutTest() {
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart item count mismatch!");
    }

    @Test
    public void checkoutWithoutInfoTest() {
        cartPage.clickCheckout();
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"),
                "Error message not shown for empty checkout form!");
    }
}
