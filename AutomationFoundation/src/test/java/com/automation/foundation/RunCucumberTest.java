package com.automation.foundation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-report/cucumber.json"},
        features = "[Your Features Directory]")
public class RunCucumberTest {
    // Run This Cucumber Test Class to Run All the Feature Files in the Desired File
}
