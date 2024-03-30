package hduappium;

import static org.testng.Assert.assertNotNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	public static int getDeviceDpi() throws IOException, NumberFormatException {
        Process process = Runtime.getRuntime().exec("adb shell wm density");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        int dpi = 0;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Physical density:")) {
                dpi = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                break;
            }
        }
        reader.close();
        
        // Ensure the process completes and the input stream is closed
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Set the interrupt flag again
            throw new IOException("Interrupted while waiting for the dpi command to complete.", e);
        }

        if (dpi == 0) {
            throw new IOException("Failed to retrieve device DPI. ADB command might not have executed properly.");
        }

        return dpi;
    }
    
    public static void main(String[] args) {
        try {
            float dpi = (float)getDeviceDpi();
            System.out.println("Device DPI: " + dpi);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve device DPI.");
        }
    }
    	
    
        
}
    																									


    