package hduappium;

import static org.testng.Assert.assertNotNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
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


public class AccessibilityTest7 extends Baseclass7 {
	
	public boolean changeFontSize(AppiumDriver driver, float d) {
	    try {
	        // Command to change the font size
	        String changeCommand = String.format("settings put system font_scale %s", d);
	        driver.executeScript("mobile: shell", ImmutableMap.of("command", changeCommand));

	        // Give the system a moment to apply the change
	        Thread.sleep(1000); // Adjust sleep time as necessary

	        // Command to retrieve the current font size setting
	        String getCommand = "settings get system font_scale";
	        String currentScale = (String) driver.executeScript("mobile: shell", ImmutableMap.of("command", getCommand));

	        // Check if the font size change was successful
	        if (Float.parseFloat(currentScale.trim()) == d) {
	            return true; // Successfully changed the font size
	        } else {
	            System.err.println("Font size did not change as expected.");
	            return false; // Failed to change the font size
	        }
	    } catch (Exception e) {
	        System.err.println("Failed to execute font scale change command: " + e.getMessage());
	        return false; // Exception occurred, indicating failure
	    }
	}

	
	@Test
	public void accessibilityTest() throws InterruptedException, IOException
    {
        
    	int minWidth = 48; 
        int minHeight = 48;
    	
    	System.out.println("Drive name is: "+driver);
    	
    	
    	
    	List<AccessibilityViolation> allViolations = new ArrayList<>();
    	
    	boolean r= changeFontSize(driver,1.5f);
        if(r==true)
        {
        	List<WebElement> textViews = driver.findElements(By.xpath("//android.widget.TextView"));
        	System.out.println(textViews.size()+"-------------------------------------------------------------------------------------------------------------------------");
        	allViolations.addAll(checkTextScalingAdaptation(textViews));
        }
    	
    	 /*List<WebElement> imageButtons = driver.findElements(By.className("android.widget.ImageButton"));
         List<WebElement> imageViews = driver.findElements(By.className("android.widget.ImageView"));
         List<WebElement> textViews = driver.findElements(By.xpath("//android.widget.TextView"));
         List<WebElement> buttons = driver.findElements(By.className("android.widget.Button"));
         List<WebElement> linkElements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]"));
         List<WebElement> checkBoxes = driver.findElements(By.className("android.widget.CheckBox"));
         List<WebElement> radioButtons = driver.findElements(By.className("android.widget.RadioButton"));
         List<WebElement> tabItems = driver.findElements(By.className("android.support.design.widget.TabLayout$TabView"));
         List<WebElement> recycleItems = driver.findElements(By.className("androidx.recyclerview.widget.RecyclerView"));
         List<WebElement> listItems = driver.findElements(By.className("androidx.recyclerview.widget.ListView"));
         List<WebElement> sliders = driver.findElements(By.className("android.widget.SeekBar"));
         List<WebElement> switches = driver.findElements(By.className("android.widget.Switch"));
         List<WebElement> toggleButtons = driver.findElements(By.className("android.widget.ToggleButton"));
         List<WebElement> dropdowns = driver.findElements(By.className("android.widget.Spinner"));
         List<WebElement> inputFields = driver.findElements(By.className("android.widget.EditText"));
         
         System.out.println(textViews.size()+"-------------------------------------------------------------------------------------------------------------------------"); */
         
        
   
        

         if (!allViolations.isEmpty()) {
             generateAccessibilityReport(allViolations);
         }
	 /*		for(int i=0; i<combinedList.size();i++)
    	{
    		
    		try {
    			
    				boolean b=contentDescription(combinedList.get(i));
    				
    				System.out.println(combinedList.get(i).getText());
    				Assert.assertTrue(b);
    				
    			} 
    		catch (AssertionError e) 
    			{
            // Record the violation message
    			violationMessages.add("Accessibility violation on element " + combinedList.get(i).getText() +"  "+ combinedList.get(i).getAttribute("class")+ " : " + e.getMessage());
    			}
    	}
    	
    	if (!violationMessages.isEmpty()) 
    	{
            System.out.println("Accessibility Violations Found:");
            for (String violation : violationMessages) {
                System.out.println(violation);
            }
    	} */
    	
    	
         	 
    	
    }
    
    
}
    																									


    