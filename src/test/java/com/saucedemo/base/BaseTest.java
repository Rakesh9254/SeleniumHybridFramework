package com.saucedemo.base;

import com.saucedemo.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected WebDriverWait wait;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    public static WebDriver getDriver() {
        return driver.get();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        log.info("=====> Setting up browser: " + browser + " <=====");
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "safari":
                webDriver = new SafariDriver();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                break;
            default:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        webDriver.manage().window().maximize();
        webDriver.get("https://www.saucedemo.com");
        driver.set(webDriver);
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        log.info("Browser opened: " + browser + ". Navigated to SauceDemo.");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("TEST FAILED: " + result.getName());
            ScreenshotUtil.captureScreenshot(getDriver(), result.getName());
        } else {
            log.info("TEST PASSED: " + result.getName());
        }
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
            log.info("Browser closed.");
        }
    }
}