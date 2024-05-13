package hduappium;

import static org.testng.Assert.assertNotNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

//Class to handle accessibility violation reports within an Appium testing context.
public class AccessibilityViolation2  {
	
	private String screenshotPath; // Path to the screenshot file of the accessibility violation
    private String violationType; // Type of accessibility violation encountered
    private String additionalDetails; // Additional details about the violation

    // Constructor to initialize an instance of AccessibilityViolation with required details
    public AccessibilityViolation2(String screenshotPath, String violationType, String additionalDetails) {
        this.screenshotPath = screenshotPath;
        this.violationType = violationType;
        this.additionalDetails = additionalDetails;
    }

    // Returns a string representation of the accessibility violation, useful for logging and reporting
    @Override
    public String toString() {
        return "Violation Type: " + violationType +
               ", Element: " + screenshotPath +
               ", Details: " + additionalDetails;
    }
    
    // Getter for the path to the screenshot of the violated element
    public String getElementDescription() {
        return screenshotPath;
    }

    // Getter for the type of accessibility violation
    public String getViolationType() {
        return violationType;
    }

    // Getter for additional details about the accessibility violation
    public String getAdditionalDetails() {
        return additionalDetails;
    }
    
        
}
    																									


    