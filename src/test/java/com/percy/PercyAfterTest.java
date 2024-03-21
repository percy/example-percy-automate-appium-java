package com.percy;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.percy.appium.PercyOnAutomate;
import io.appium.java_client.AppiumDriver;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

class PercyAfterTest {

    public PercyOnAutomate percy ;
    public WebDriverWait webDriverWait;

    AppiumDriver driver;

    public String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public String AUTOMATE_KEY =  System.getenv("BROWSERSTACK_ACCESS_KEY");
    public String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @BeforeMethod(alwaysRun = true)
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("osVersion", "14");
        browserstackOptions.put("deviceName", "iPhone 12");
        browserstackOptions.put("local", "false");
        browserstackOptions.put("realMobile", true);
        browserstackOptions.put("projectName", "Percy");
        browserstackOptions.put("buildName", "Appium-SDKs");
        browserstackOptions.put("sessionName", "ios12-java");
        
        capabilities.setCapability("bstack:options", browserstackOptions);


        // Create sessioin
        driver = new AppiumDriver(new URL(URL), capabilities);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void addProductToCart() throws Exception {

        try{
           //webdriver initilization
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
           //percy initilization
        percy = new PercyOnAutomate(driver);

        // navigate to bstackdemo
        driver.get("https://www.bstackdemo.com");

           // Check the title
        webDriverWait.until(ExpectedConditions.titleContains("StackDemo"));

           // click on the samsung product
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='__next']/div/div/main/div[1]/div[2]/label/span")));
        driver.findElement(By.xpath("//*[@id='__next']/div/div/main/div[1]/div[2]/label/span")).click();

           // [percy note: important step]
           // Percy Screenshot 1
           // take percy_screenshot using the following command
        percy.screenshot("screenshot_1");

           // Save the text of the product for later verify
        String productOnScreenText = driver.findElement(By.xpath("//*[@id=\"10\"]/p")).getText();

           // Click on add to cart button
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"10\"]/div[4]")));
        driver.findElement(By.xpath("//*[@id=\"10\"]/div[4]")).click();

           // See if the cart is opened or not
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("float-cart__content")));

           // Get text of product in cart
        String productOnCartText = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]")).getText();

           // [percy note: important step]
           // Percy Screenshot 2
           // take percy_screenshot using the following command
        percy.screenshot("screenshot_2");

        Assert.assertEquals(productOnScreenText, productOnCartText);

        } catch (Exception e) {
            System.out.println("Error occured while executing script :" + e);
        }
    }
}
