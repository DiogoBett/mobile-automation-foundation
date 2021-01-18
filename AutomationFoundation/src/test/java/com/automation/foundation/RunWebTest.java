package com.automation.foundation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-report/cucumber.json"},
        features = "src/test/resources/com/automation/foundation/webFeatures")
public class RunWebTest {
    // Run This Cucumber Test Class to Run all the Web Automated Tests
}
