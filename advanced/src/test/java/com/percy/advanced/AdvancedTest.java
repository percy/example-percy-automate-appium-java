package com.percy.advanced;

// PER-8195 Phase 3 — automate-appium-java advanced example.

import io.appium.java_client.AppiumDriver;
import io.percy.appium.PercyOnAutomate;
import org.openqa.selenium.MutableCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdvancedTest {
    private AppiumDriver driver;
    private PercyOnAutomate percy;

    @BeforeClass
    public void setUp() throws Exception {
        String user = System.getenv("BROWSERSTACK_USERNAME");
        String key = System.getenv("BROWSERSTACK_ACCESS_KEY");
        MutableCapabilities caps = new MutableCapabilities();
        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("osVersion", System.getenv().getOrDefault("OS_VERSION", "12.0"));
        bstackOptions.put("deviceName", System.getenv().getOrDefault("DEVICE", "Samsung Galaxy S22 Ultra"));
        bstackOptions.put("projectName", System.getenv().getOrDefault("PERCY_PROJECT", "Percy Automate Appium-Java Advanced"));
        bstackOptions.put("buildName", System.getenv().getOrDefault("PERCY_BUILD", "Advanced Automate Appium Java"));
        bstackOptions.put("sessionName", "advanced_visual_test");
        caps.setCapability("bstack:options", bstackOptions);
        caps.setCapability("app", System.getenv("APP"));
        driver = new AppiumDriver(
            new URL("https://" + user + ":" + key + "@hub-cloud.browserstack.com/wd/hub"),
            caps);
        percy = new PercyOnAutomate(driver);
        Thread.sleep(5000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void exercisesBaseline() {
        percy.screenshot("Wikipedia Home");
    }

    @Test
    public void exercisesDeviceNameAndOrientation() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("device_name", System.getenv().getOrDefault("DEVICE", "Samsung Galaxy S22 Ultra"));
        opts.put("orientation", "landscape");
        percy.screenshot("Wikipedia Home — landscape", opts);
    }

    @Test
    public void exercisesFullscreenAndBars() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("fullscreen", true);
        opts.put("status_bar_height", 24);
        opts.put("nav_bar_height", 0);
        percy.screenshot("Wikipedia Home — fullscreen", opts);
    }

    @Test
    public void exercisesIgnoreRegionsViaXpath() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("ignore_regions_xpaths",
            Arrays.asList("//android.widget.TextView[@text=\"Search Wikipedia\"]"));
        percy.screenshot("Wikipedia Home — ignore via xpath", opts);
    }

    @Test
    public void exercisesCustomIgnoreRegions() {
        Map<String, Object> region = new HashMap<>();
        region.put("top", 0); region.put("bottom", 100);
        region.put("left", 0); region.put("right", 300);
        Map<String, Object> opts = new HashMap<>();
        opts.put("custom_ignore_regions", Arrays.asList(region));
        percy.screenshot("Wikipedia Home — custom ignore region", opts);
    }

    @Test
    public void exercisesConsiderRegionsViaXpath() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("consider_regions_xpaths",
            Arrays.asList("//android.widget.TextView[@text=\"Search Wikipedia\"]"));
        percy.screenshot("Wikipedia Home — consider via xpath", opts);
    }

    @Test
    public void exercisesSyncMode() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("sync", true);
        percy.screenshot("Wikipedia Home — sync", opts);
    }

    @Test
    public void exercisesTestCaseAndLabels() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("test_case", "home-smoke");
        opts.put("labels", "smoke,automate-appium-java");
        percy.screenshot("Wikipedia Home — test_case + labels", opts);
    }
}
