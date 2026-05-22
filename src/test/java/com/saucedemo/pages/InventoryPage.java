package com.saucedemo.pages;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    WebDriver driver;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = "[data-test='product-sort-container']")
    private WebElement sortDropdown;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addFirstProductToCart() {
        addToCartButtons.get(0).click();
    }

    public void addProductToCartByIndex(int index) {
        addToCartButtons.get(index).click();
    }

    public String getCartCount() {
        return cartBadge.getText();
    }

    public boolean isCartBadgeDisplayed() {
        try {
            return cartBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void goToCart() {
        cartIcon.click();
    }

    public int getInventoryItemCount() {
        return inventoryItems.size();
    }

    public String getFirstProductName() {
        return productNames.get(0).getText();
    }

    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getAllProductPrices() {
        return productPrices.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public void clickMenu() {
        menuButton.click();
    }

    public void clickLogout() {
        logoutLink.click();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        clickMenu();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        clickLogout();
    }

    public void selectSortOption(String value) {
        Select select = new Select(sortDropdown);
        select.selectByValue(value);
    }
}
