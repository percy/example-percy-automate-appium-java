package com.percy.advanced;

// PER-8195 Phase 3 — automate-appium-java advanced example.
//
// Percy on Automate with Appium captures a mobile *browser* session (Chrome on a real
// Android device), the same flow as src/test/java/com/percy/PercyTest.java. Native apps
// belong to App Percy (example-percy-appium-java): the CLI's Automate capture runs
// JavaScript to read the screen size and regions, which a native app session cannot do.
//
// PercyOnAutomate logs and swallows capture errors, so `make test` fails the run when
// the Percy log reports one (see Makefile).

import io.appium.java_client.android.AndroidDriver;
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
    private static final String HEADER_XPATH = "//h1";

    private AndroidDriver driver;
    private PercyOnAutomate percy;

    @BeforeClass
    public void setUp() throws Exception {
        String user = System.getenv("BROWSERSTACK_USERNAME");
        String key = System.getenv("BROWSERSTACK_ACCESS_KEY");
        MutableCapabilities caps = new MutableCapabilities();
        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("osVersion", System.getenv().getOrDefault("OS_VERSION", "12.0"));
        bstackOptions.put("deviceName", System.getenv().getOrDefault("DEVICE", "Samsung Galaxy S22 Ultra"));
        bstackOptions.put("appiumVersion", System.getenv().getOrDefault("APPIUM_VERSION", "2.19.0"));
        bstackOptions.put("projectName", System.getenv().getOrDefault("PERCY_PROJECT", "Percy Automate Appium-Java Advanced"));
        bstackOptions.put("buildName", System.getenv().getOrDefault("PERCY_BUILD", "Advanced Automate Appium Java"));
        bstackOptions.put("sessionName", "advanced_visual_test");
        caps.setCapability("platformName", "Android");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("bstack:options", bstackOptions);
        driver = new AndroidDriver(
            new URL("https://" + user + ":" + key + "@hub-cloud.browserstack.com/wd/hub"),
            caps);
        driver.get(System.getenv().getOrDefault("URL", "https://en.wikipedia.org/wiki/BrowserStack"));
        percy = new PercyOnAutomate(driver);
        Thread.sleep(5000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void exercisesBaseline() {
        percy.screenshot("Wikipedia Article");
    }

    @Test
    public void exercisesFullPage() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("full_page", true);
        percy.screenshot("Wikipedia Article — full page", opts);
    }

    @Test
    public void exercisesIgnoreRegionsViaXpath() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("ignore_region_xpaths", Arrays.asList(HEADER_XPATH));
        percy.screenshot("Wikipedia Article — ignore via xpath", opts);
    }

    @Test
    public void exercisesIgnoreRegionsViaSelector() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("ignore_region_selectors", Arrays.asList("h1"));
        percy.screenshot("Wikipedia Article — ignore via selector", opts);
    }

    @Test
    public void exercisesCustomIgnoreRegions() {
        Map<String, Object> region = new HashMap<>();
        region.put("top", 0); region.put("bottom", 100);
        region.put("left", 0); region.put("right", 300);
        Map<String, Object> opts = new HashMap<>();
        opts.put("custom_ignore_regions", Arrays.asList(region));
        percy.screenshot("Wikipedia Article — custom ignore region", opts);
    }

    @Test
    public void exercisesConsiderRegionsViaXpath() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("consider_region_xpaths", Arrays.asList(HEADER_XPATH));
        percy.screenshot("Wikipedia Article — consider via xpath", opts);
    }

    @Test
    public void exercisesSyncMode() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("sync", true);
        percy.screenshot("Wikipedia Article — sync", opts);
    }

    @Test
    public void exercisesTestCaseAndLabels() {
        Map<String, Object> opts = new HashMap<>();
        opts.put("test_case", "home-smoke");
        opts.put("labels", "smoke,automate-appium-java");
        percy.screenshot("Wikipedia Article — test_case + labels", opts);
    }
}
