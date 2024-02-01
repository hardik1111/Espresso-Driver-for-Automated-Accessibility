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


public class AccessibilityTest5  {
	
	public static void main(String[] args) {
        try {
            // Set up desired capabilities
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("deviceName", "Pixel 6 API 33"); // Replace with your device name or emulator
            caps.setCapability("automationName", "Espresso");
            caps.setCapability("app", "C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/resources/ApiDemos-debug.apk");
            caps.setCapability("appPackage", "io.appium.android.apis"); 
            // Replace with your app's package name
            // Replace with app's MainActivity

            // Initialize the Appium driver (Make sure Appium server is running)
            AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);

           
            // Quit the driver
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    	
    
        
}
    																									


    