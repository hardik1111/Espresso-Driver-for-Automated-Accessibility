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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class Accessibot_TextScaling extends Baseclass_TextScaling {
	
	List<AccessibilityViolation2> allViolations = new ArrayList<>();
	
	
	
	
	private boolean scrollForward(String scrollableUiSelector) {
	    try {
	    	// Execute the scroll operation on the given UI selector which should be scrollable
	        driver.findElement(AppiumBy.androidUIAutomator(scrollableUiSelector + ".scrollForward()"));
	     // Introduce a short pause to allow the UI to update after the scroll
	        Thread.sleep(1000);  // Adjust based on your app's response time
	        return true; // Return true if the scroll operation is successful
	    } catch (NoSuchElementException | InterruptedException e) {
	    	// Handle exceptions if the element is not found or the thread is interrupted
	        return false; // Return false indicating that scrolling failed or there's no more content
	    }
	}

	private boolean isThereNewContent(String allElementsXpath, Set<String> previousSignatures) {
		// Retrieve a list of web elements using the specified Xpath to capture the current state of visible elements
	    List<WebElement> elementsAfterScroll = driver.findElements(By.xpath(allElementsXpath));
	    // Transform the list of elements into a set of unique signatures based on their location and size
	    Set<String> newSignatures = elementsAfterScroll.stream()
	            .map(element -> element.getLocation().toString() + "|" + element.getSize().toString())
	            .collect(Collectors.toSet());

	    // Remove all previously captured signatures to see if there are any new ones
	    newSignatures.removeAll(previousSignatures);
	    // Return true if there are new signatures, which indicates new content is visible after the scroll
	    return !newSignatures.isEmpty();
	}

	/*private void collectAllElements() throws IOException, InterruptedException {
	    int minWidth = 48; // Minimum width threshold for element
	    int minHeight = 48; // Minimum height threshold for element
	    String scrollableUiSelector = "new UiScrollable(new UiSelector().scrollable(true))";
	    Set<String> previousSignatures = new HashSet<>(); // Set to track element uniqueness

	    while (true) {
	        Thread.sleep(1000); // Delay to allow UI to settle
	       
	        // Find all elements that are of Android widget types
	        List<WebElement> currentScreenElements = driver.findElements(By.xpath("//*[contains(@class, 'android.widget')]"));
	        
	        // Specifically find elements that are links (TextViews containing URLs)
	        List<WebElement> currentScreenLinkElements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]"));
	        
	        // Categorize elements for different accessibility checks
	        Map<String, List<WebElement>> categorizedElements = categorizeElements(currentScreenElements, currentScreenLinkElements);
	        
	        // Perform accessibility checks and add results to allViolations
	        allViolations.addAll(performAccessibilityChecks(categorizedElements, minWidth, minHeight, allViolations));
	        
	        // Collect signatures of elements to detect new content in subsequent scrolls
	        previousSignatures.addAll(currentScreenElements.stream()
	                .map(element -> element.getLocation().toString() + "|" + element.getSize().toString())
	                .collect(Collectors.toSet()));

	        // Attempt to scroll forward and check for new content
	        if (!scrollForward(scrollableUiSelector) || !isThereNewContent("//*[contains(@class, 'android.widget')]", previousSignatures)) {
	            break; // Break the loop if no new scrollable content or new elements are detected
	        }
	    }
	}*/
	
	
	private boolean scrollLogic() throws IOException, InterruptedException {
	    //int minWidth = 48; // Minimum width threshold for element
	    //int minHeight = 48; // Minimum height threshold for element
	    String scrollableUiSelector = "new UiScrollable(new UiSelector().scrollable(true))";
	    Set<String> previousSignatures = new HashSet<>(); // Set to track element uniqueness

	        Thread.sleep(1000); // Delay to allow UI to settle
	       
	        // Find all elements that are of Android widget types
	        List<WebElement> currentScreenElements = driver.findElements(By.xpath("//*[contains(@class, 'android.widget')]"));
	        
	        // Specifically find elements that are links (TextViews containing URLs)
	        //List<WebElement> currentScreenLinkElements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]"));
	        
	        // Categorize elements for different accessibility checks
	        //Map<String, List<WebElement>> categorizedElements = categorizeElements(currentScreenElements, currentScreenLinkElements);
	        
	        // Perform accessibility checks and add results to allViolations
	       // allViolations.addAll(performAccessibilityChecks(categorizedElements, minWidth, minHeight, allViolations));
	        
	        // Collect signatures of elements to detect new content in subsequent scrolls
	        previousSignatures.addAll(currentScreenElements.stream()
	                .map(element -> element.getLocation().toString() + "|" + element.getSize().toString())
	                .collect(Collectors.toSet()));

	        // Attempt to scroll forward and check for new content
	        if (scrollForward(scrollableUiSelector) && isThereNewContent("//*[contains(@class, 'android.widget')]", previousSignatures)) {
	            return true; // Return true if scrolled and new content found
	        } else {
	            return false; // Return false if no new scrollable content or new elements are detected
	        }
	    
	}

	public List<SavedElement> extractAndSaveElements(String combinedXpath) throws IOException, InterruptedException {
	    
		String xpath = combinedXpath;
		int i = 0;

	    List<SavedElement> savedElements = new ArrayList<>();
	    
	    int lastIndexProcessed = 0;
	    boolean retry = true;

	    while (retry) {
	        retry = false;
	        try {
	        	List<WebElement> elements = driver.findElements(By.xpath(xpath));
	            System.out.println(elements.size());
	            while (lastIndexProcessed < elements.size()) {
	                WebElement element = elements.get(lastIndexProcessed);

	                Map<String, String> attributes = new HashMap<>();
	                
	                attributes.put("text", element.getText());
	                

	                Dimension size = element.getSize();
	                int width = size.getWidth();
	                int height = size.getHeight();

	                List<ChildElement> childElements = new ArrayList<>();
	                String className = element.getAttribute("class");
	                if (className.equals("android.widget.LinearLayout") ||
	                    className.equals("android.widget.RelativeLayout") ||
	                    className.equals("android.widget.FrameLayout")) {
	                    childElements = extractChildElements(element);
	                }

	                String screenshotPath = takeElementScreenshot1(element, i);
	                savedElements.add(new SavedElement(attributes, childElements, width, height, screenshotPath));
	                i++;
	                lastIndexProcessed++;
	            }
	        } catch (StaleElementReferenceException e) {
	            System.out.println("Encountered StaleElementReferenceException, retrying...");
	            retry = true;
	            // Optionally, you can add a short delay before retrying
	            try {
	                Thread.sleep(500); // 0.5 seconds delay
	            } catch (InterruptedException ie) {
	                Thread.currentThread().interrupt();
	                throw new RuntimeException(ie);
	            }
	        }
	    }
	    
	    if(scrollLogic())
	    {
	    	extractAndSaveElements(xpath);
	    }
	    

	    return savedElements;
		
	}




	    private List<ChildElement> extractChildElements(WebElement parentElement) {
	        List<WebElement> childWebElements = parentElement.findElements(By.xpath(".//*"));
	        List<ChildElement> childElements = new ArrayList<>();

	        for (WebElement childElement : childWebElements) {
	            String className = childElement.getAttribute("class");
	            if (className.equals("android.widget.TextView") ||
	                className.equals("android.widget.EditText") ||
	                className.equals("android.widget.CheckBox")) {
	                Map<String, String> childAttributes = new HashMap<>();
	                childAttributes.put("text", childElement.getText());
	                // Add more attributes as needed

	                childElements.add(new ChildElement(childAttributes));
	            }
	        }

	        return childElements;
	    }

	    private void performChecksForScreen(String screenName,String combinedXpath) throws IOException, InterruptedException {
	        //addDomChangeListener(); // Add DOM change listener before starting the checks
	    	
	    	List<SavedElement> savedElements = extractAndSaveElements(combinedXpath);
	       // List<WebElement> CDelements = extractElements();
	        
	        allViolations.addAll(compareTextWithOCR(savedElements));
	        
	        
	      

	        if (!allViolations.isEmpty()) {
	            generatePdfReport(allViolations, screenName); // Assuming you modify generatePdfReport to accept screenName for a more descriptive report
	            allViolations.clear(); // Clear the list for the next screen
	        }
	    }

	    @Test
	    public void accessibilityTest() throws InterruptedException, IOException {
	        changeFontSize(1.0f);
	        System.out.println("Driver name is: " + driver);
	        if(changeFontSize(1.5f)) {
	        List<String> elementTypes = Arrays.asList(
	    	        "android.widget.TextView",
	    	        
	    	        "android.widget.Button",
	    	        "android.widget.CheckBox",
	    	        "android.widget.RadioButton",
	    	        "android.widget.ToggleButton",
	    	        
	    	        "android.widget.Switch",
	    	        
	    	        "android.widget.EditText",
	    	        
	    	        "android.support.design.widget.TabLayout$TabView"
	    	        
	    	    );

	    	    String xpathExpression = elementTypes.stream()
	    	        .map(type -> "contains(@class, '" + type + "')")
	    	        .collect(Collectors.joining(" or ", "//*[", "]"));
	    	    
	    	    //String urlTextViewsXpath = "//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]";
	    	  //  String combinedXpath = xpathExpression + " | " + urlTextViewsXpath;
	        
	        performChecksForScreen("Trip Results Page",xpathExpression);

	        // Change font size back to normal after tests if necessary
	        changeFontSize(1.0f);
	    }
	   }
    
    
}
    																									


    