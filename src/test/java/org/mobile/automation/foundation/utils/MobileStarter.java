package org.mobile.automation.foundation.utils;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MobileStarter {

    // Project-wide Properties File
    private static Properties properties;

    // Cucumber Methods
    @Given("^I have started the Android Application$")
    public void startAndroidApp1() {

        // Testing Android
        MobileUtility.setTestingAndroid(true);

        // Set Android App Variant (Optional)
        setAndroidAppVariant("");

        // Starts the App
        MobileUtility.startAndroidApp();
    }

    @Given("^I have started the iOS Application$")
    public void startIOSApp1() {

        // Testing iOS
        MobileUtility.setTestingIOS(true);

        // Set Android App Variant (Optional)
        setIOSdAppVariant("");

        // Starts the App
        MobileUtility.startIOSApp();
    }

    @After
    public void stopApplications(Scenario scenario) {

        if (scenario.isFailed()) {
            MobileUtility.takeScreenshot(scenario.getId());
        }

        if (MobileUtility.isTestingAndroid()) {

            // Testing Android
            MobileUtility.setTestingAndroid(false);

            // Reset App Variant
            setAndroidAppVariant(null);

            // Close Android App
            MobileUtility.getAndroidDriver().closeApp();

            // Completing Test
            Assert.assertFalse(MobileUtility.isTestingAndroid());
        }

        if (MobileUtility.isTestingIOS()) {
            // Testing iOS
            MobileUtility.setTestingIOS(false);

            // Reset App Variant
            setIOSdAppVariant(null);

            // Close iOS App
            MobileUtility.getIosDriver().closeApp();

            // Completing Test
            Assert.assertFalse(MobileUtility.isTestingIOS());
        }

    }

    // App Variant Setup Methods
    private void setAndroidAppVariant(String appVariant) {
        // Set App Android Variants Value (Optional)
    }

    private void setIOSdAppVariant(String appVariant) {
        // Set App iOS Variants Value (Optional)
    }

    // Access Project-wide Properties
    static String getValue(String key) {

        try {
            properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException ex ) {
            System.out.println(ex.getMessage());
        }

        return properties.getProperty(key);
    }

}
