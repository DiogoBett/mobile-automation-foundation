package org.mobile.automation.foundation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"json:target/cucumber-report/cucumber.json"},
        features = "src/test/resources/features/android")
public class AndroidRunner {
    // Run This Cucumber Test Class to Run all the Android Automated Tests
}
