package com.automation.foundation.androidDefs;

import com.automation.foundation.utilityClasses.MobileUtility;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class androidExampleDefs {

    // Basic Required Variables
    private static AndroidDriver<MobileElement> driver = MobileUtility.getAndroidDriver();

    @Given("^I do something in the App - Android$")
    public void something1() {
        // Cucumber Given Method Implementation

        // Example Below:
        WebElement goToFormButton = driver.findElement(By.id("[Element ID]"));
        goToFormButton.click(); // Clicks the Selected Element
    }

    @When("^I do this in the App - Android$")
    public void something2() {
        // Cucumber When Method Implementation

        // Example Below:
        WebElement textFormField = driver.findElement(By.xpath("[Element XPath]"));
        textFormField.clear(); // Clears the Input Field
        textFormField.sendKeys("Example"); // Write in the Input Field
    }

    @Then("^This should happen - Android$")
    public void something3() {
        // Cucumber Then Method Implementation

        // Example Below:
        WebElement submitFormButton = driver.findElement(By.name("[Element Name]"));
        submitFormButton.click(); // Clicks the Selected Element
    }


}
