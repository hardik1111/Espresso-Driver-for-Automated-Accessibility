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


public class AccessibilityViolation extends Baseclass7 {
	
	private String elementDescription;
    private String violationType;
    private String additionalDetails;

    public AccessibilityViolation(String elementDescription, String violationType, String additionalDetails) {
        this.elementDescription = elementDescription;
        this.violationType = violationType;
        this.additionalDetails = additionalDetails;
    }

    @Override
    public String toString() {
        return "Violation Type: " + violationType +
               ", Element: " + elementDescription +
               ", Details: " + additionalDetails;
    }
    
    public String getElementDescription() {
        return elementDescription;
    }

    public String getViolationType() {
        return violationType;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }
    
        
}
    																									


    