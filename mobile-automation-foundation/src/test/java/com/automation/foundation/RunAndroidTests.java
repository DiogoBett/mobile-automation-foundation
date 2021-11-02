package com.automation.foundation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"json:target/cucumber-report/cucumber.json"},
        features = "src/test/resources/com.automation.foundation/androidFeatures")
public class RunAndroidTests {
    // Run This Cucumber Test Class to Run all the Android Automated Tests
}
