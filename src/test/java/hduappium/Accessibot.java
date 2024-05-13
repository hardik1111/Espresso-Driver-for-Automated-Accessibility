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
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
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
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public class Accessibot extends Baseclass12 {
	
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

	private void collectAllElements() throws IOException, InterruptedException {
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
	}
	
	private void collectAllElements1() throws IOException, InterruptedException {
	    
	
	   
	    String scrollableUiSelector = "new UiScrollable(new UiSelector().scrollable(true))";
	    //String allElementsXpath = "";
	    Set<String> previousSignatures = new HashSet<>();

	    while (true) {
	    	Thread.sleep(1000);
	        List<WebElement> currentTextElements = driver.findElements(By.xpath("//android.widget.TextView"));
	        //List<WebElement> currentScreenLinkElements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]"));
	        
	        allViolations.addAll(checkTextScalingAdaptation(currentTextElements));

	        // Update signatures before attempting to scroll
	        previousSignatures.addAll(currentTextElements.stream()
	                .map(element -> element.getLocation().toString() + "|" + element.getSize().toString())
	                .collect(Collectors.toSet()));

	        if (!scrollForward(scrollableUiSelector) || !isThereNewContent("//android.widget.TextView", previousSignatures)) {
	            break; // Exit if unable to scroll further or no new content detected
	        }
	    }

	        // Update signatures before attempting to scrol        

	    
	}
	// Ensure categorizeElements, performAccessibilityChecks, and generateAccessibilityReport methods are defined and correctly implemented.

	
	/*private List<WebElement> collectAllTextElements(AndroidDriver driver) {
		List<WebElement> allTextElements = new ArrayList<>();
		//List<WebElement> linkElements = new ArrayList<>();
	    String scrollableUiSelector = "new UiScrollable(new UiSelector().scrollable(true))";
	    boolean moreContent;

	    do {
	        // Collect elements before attempting to scroll
	        List<WebElement> currentScreenElements = driver.findElements(By.xpath("//android.widget.TextView"));
	        //List<WebElement> CuurentScreenLinkElements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]"));
	        allTextElements.addAll(currentScreenElements);
	        //linkElements.addAll(CuurentScreenLinkElements);
	        // Attempt to scroll and check if more content is available
	        moreContent = scrollForward(driver, scrollableUiSelector);
	    } while (moreContent);
	    
	    return allTextElements;
	  // Remove duplicates based on WebElement reference
	}*/
	
	private Map<String, List<WebElement>> categorizeElements(List<WebElement> allElements,List<WebElement> linkElements) {
		
			// Initialize a map to store categories of WebElements
		    Map<String, List<WebElement>> categorizedElements = new HashMap<>();
		    
		    // Categorize elements based on their type for accessibility checks (content description)
		    categorizedElements.put("contentDescription", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton", "android.widget.TextView","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.Switch","android.widget.SeekBar","android.widget.EditText","android.widget.LinearLayout", "android.widget.FrameLayout", "android.widget.RelativeLayout")));
		    List<WebElement> contentDescriptionElements = categorizedElements.computeIfAbsent("contentDescription", k -> new ArrayList<>());
		    contentDescriptionElements.addAll(linkElements); // Add link elements to content description category
		    
		    // Categorize elements by their suitability as touch targets
		    categorizedElements.put("touchTarget", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton", "android.widget.TextView","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","android.widget.SeekBar","android.widget.Switch", "android.widget.LinearLayout", "android.widget.FrameLayout", "android.widget.RelativeLayout")));
		    List<WebElement> touchTargetElements = categorizedElements.computeIfAbsent("touchTarget", k -> new ArrayList<>());
		    touchTargetElements.addAll(linkElements); // Include link elements as touch targets
		    
		    // Categorize elements by their focusability for accessibility
		    categorizedElements.put("focusable", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton", "android.widget.TextView","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","android.widget.SeekBar","android.widget.Switch", "android.widget.LinearLayout", "android.widget.FrameLayout", "android.widget.RelativeLayout")));
		    List<WebElement> focusableElements = categorizedElements.computeIfAbsent("focusable", k -> new ArrayList<>());
		    focusableElements.addAll(linkElements); // Add link elements to focusable category
		    
		    // Categorize elements by their visibility on screen
		    categorizedElements.put("checkVisibility", filterElementsBy(allElements, Arrays.asList("android.widget.TextView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.EditText","android.widget.ImageButton","android.widget.Switch","android.widget.ToggleButton","android.widget.Spinner","android.widget.SeekBar","android.support.design.widget.TabLayout$TabView", "android.widget.ActionMenuView","android.widget.ListView", "android.widget.GridView","android.widget.LinearLayout", "android.widget.FrameLayout", "android.widget.RelativeLayout" )));
		    List<WebElement> checkVisibilityElements = categorizedElements.computeIfAbsent("checkVisibility", k -> new ArrayList<>());
		    checkVisibilityElements.addAll(linkElements); // Ensure visible elements include links
		    
		    // Categorize interactive elements that can receive user actions
		    categorizedElements.put("interactive", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.SeekBar","android.widget.Switch","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","androidx.drawerlayout.widget.DrawerLayout","android.widget.PopupMenu","android.view.Menu" )));
		    
		    // Categorize non-text elements for color contrast accessibility
		    categorizedElements.put("colorContrastNonText", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","android.widget.SeekBar","android.widget.Switch")));

		    // Categorize text elements specifically for color contrast accessibility
		    categorizedElements.put("colorContrastText", filterElementsBy(allElements, Arrays.asList("android.widget.TextView")));
		    List<WebElement> colorContrastTextElements = categorizedElements.computeIfAbsent("colorContrast", k -> new ArrayList<>());
		    colorContrastTextElements.addAll(linkElements); // Text elements for color contrast analysis
		    
		    // Specifically categorize all TextView elements for separate processing if needed
		    categorizedElements.put("textView", filterElementsBy(allElements, Arrays.asList("android.widget.TextView")));
		    
		    return categorizedElements; // Return the categorized map of elements
		}
	

	private List<WebElement> filterElementsBy(List<WebElement> elements, List<String> classNames) {
	    // Use Java Streams to filter and collect elements
	    return elements.stream() // Convert list of elements to a Stream for processing
	            .filter(e -> classNames.contains(e.getAttribute("className"))) // Filter elements where their className attribute is in the classNames list
	            .collect(Collectors.toList()); // Collect the filtered elements back into a List
	}

	
	
	
	private List<AccessibilityViolation2> performAccessibilityChecks(Map<String, List<WebElement>> categorizedElements, int minWidth, int minHeight, List<AccessibilityViolation2> allViolations) throws IOException {
	    // Add violations found in the 'contentDescription' category using the checkContentDescription method
	    allViolations.addAll(checkContentDescription(categorizedElements.get("contentDescription")));

	    // Add violations related to touch target sizes that do not meet the specified minimum width and height
	    allViolations.addAll(checkTouchTargetSize(categorizedElements.get("touchTarget"), minWidth, minHeight));

	    // Add violations found in the 'focusable' category elements to ensure they are properly focusable
	    allViolations.addAll(checkFocusability(categorizedElements.get("focusable")));

	    // Add violations concerning visibility and readability issues in the 'checkVisibility' category
	    allViolations.addAll(checkVisibilityAndReadability(categorizedElements.get("checkVisibility")));

	    // Add violations related to the states of interactive elements, ensuring they are appropriate
	    allViolations.addAll(checkInteractiveElementStates(categorizedElements.get("interactive")));

	    // Add violations for non-text elements that fail color contrast standards
	    allViolations.addAll(checkColorContrastNonText(categorizedElements.get("colorContrastNonText")));

	    // Add violations for text elements that do not meet the color contrast requirements
	    allViolations.addAll(checkColorContrastText(categorizedElements.get("colorContrastText")));
	    
	    // Return the list of all accumulated accessibility violations
	    return allViolations;
	}

	
	private void performChecksForScreen(String screenName) throws IOException, InterruptedException {
	    // Your existing logic to navigate to a specific screen and perform checks
	    // Example: navigateToHomePage() or navigateToDetailsPage()
	   
		collectAllElements(); // This fills the allViolations list

	    // Generate report for the current screen
	    if (!allViolations.isEmpty()) {
	        generatePdfReport(allViolations, screenName); // Assuming you modify generatePdfReport to accept screenName for a more descriptive report
	        allViolations.clear(); // Clear the list for the next screen
	    }
	}
	
	private void performChecksForScreen1(String screenName) throws IOException, InterruptedException {
	    // Your existing logic to navigate to a specific screen and perform checks
	    // Example: navigateToHomePage() or navigateToDetailsPage()
	   
		collectAllElements1(); // This fills the allViolations list

	    // Generate report for the current screen
	    if (!allViolations.isEmpty()) {
	        generatePdfReport(allViolations, screenName); // Assuming you modify generatePdfReport to accept screenName for a more descriptive report
	        allViolations.clear(); // Clear the list for the next screen
	    }
	}
	
	private void navigateToTutorialPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(By.id("de.hafas.android.standard4:id/button_tracking_optin_agree")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToHomePage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(AppiumBy.accessibilityId("Close tutorial")).click();
    	Thread.sleep(5000);
    	driver.switchTo().alert().accept();
    	Thread.sleep(5000);
    	driver.findElement(By.xpath("//android.widget.LinearLayout[@content-desc=\"Product filter Favorites inactive\"]/android.widget.ImageView")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToSideDrawer() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
	}
	private void navigateToTripPlannerPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[3]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToDeparturesPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[4]")).click();
    	Thread.sleep(20000);
	}
	
	private void navigateToMapSettingsPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[5]")).click();
    	Thread.sleep(5000);
    	driver.findElement(AppiumBy.accessibilityId("Map Settings")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToLocationSearchPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
		Thread.sleep(5000);
		driver.findElement(AppiumBy.accessibilityId("Location search")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToStopsNearByPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
    	driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Stops nearby\"]/android.view.View[1]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToPOINearByPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		//driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
		driver.findElement(By.id("android:id/button1")).click();
		Thread.sleep(5000);
    	driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"POIs nearby\"]/android.view.View[1]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToNearByListPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		//driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
		driver.findElement(By.id("android:id/button1")).click();
		Thread.sleep(5000);
		driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
		Thread.sleep(5000);
    	driver.findElement(AppiumBy.accessibilityId("Map-mode List")).click();
    	Thread.sleep(5000);
	}
	private void navigateToMapMaterialPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(AppiumBy.accessibilityId("Close details to map object and cancel selection")).click();
		Thread.sleep(5000);
    	driver.findElement(AppiumBy.accessibilityId("Choose map")).click();
    	Thread.sleep(5000);
	}
	private void navigateToAlarmPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		driver.findElement(By.id("de.hafas.android.standard4:id/button_map_overlay_finish")).click();
		Thread.sleep(5000);
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[6]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToAlarmsHistoryPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("History")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToMyTripsPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[7]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToMyTripsEndedPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Ended")).click();
		Thread.sleep(5000);
	}
	
	private void navigateToEventsPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[9]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToNewsPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[10]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToSettingsPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[11]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToLegalNoticePage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[12]")).click();
    	Thread.sleep(5000);
	}
	
	private void navigateToNetworkMapsPage() throws InterruptedException {
	    // Logic to ensure the app is on the Home screen
	    // E.g., clicking the "Home" icon or using the back button
		
		driver.findElement(AppiumBy.accessibilityId("Show navigation drawer")).click();
    	Thread.sleep(5000);
		driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[14]")).click();
    	Thread.sleep(5000);
	}

	
	@Test
	public void accessibilityTest() throws InterruptedException, IOException
    {
        
		//setEmulatorLocation(0,0);
		changeFontSize(1.0f);
    	System.out.println("Drive name is: "+driver);
    	performChecksForScreen("InitialScreen");
    	navigateToTutorialPage();
    	performChecksForScreen("Turorial");
    	navigateToHomePage();
    	performChecksForScreen("HomeScreen");
    	//navigateToSideDrawer();
    	//performChecksForScreen("NavigationDrawer");
    	navigateToTripPlannerPage();
    	performChecksForScreen("TripPlanner");
    	navigateToDeparturesPage();
    	performChecksForScreen("Departures");
    	navigateToMapSettingsPage();
    	performChecksForScreen("MapSettings");
    	navigateToLocationSearchPage();
    	performChecksForScreen("LocationSearch");
    	navigateToStopsNearByPage();
    	performChecksForScreen("StopsNearby");
    	navigateToPOINearByPage();
    	performChecksForScreen("POINearBy");
    	navigateToNearByListPage();
    	performChecksForScreen("NearByList");
    	navigateToMapMaterialPage();
    	performChecksForScreen("MapMaterial");
    	navigateToAlarmPage();
    	performChecksForScreen("Alarms Page");
    	navigateToAlarmsHistoryPage();
    	performChecksForScreen("AlarmsHistory");
    	navigateToMyTripsPage();
    	performChecksForScreen("MyTrips");
    	navigateToMyTripsEndedPage();
    	performChecksForScreen("MyTripsEnded");
    	navigateToEventsPage();
    	performChecksForScreen("Events");
    	navigateToNewsPage();
    	performChecksForScreen("News");
    	navigateToSettingsPage();
    	performChecksForScreen("Settings");
    	navigateToLegalNoticePage();
    	performChecksForScreen("LegalNotice");
    	navigateToNetworkMapsPage();
    	performChecksForScreen("NetworkMaps");
    	
    	//collectAllElements(driver);
    	/*driver.findElement(By.id("de.hafas.android.standard4:id/button_tracking_optin_agree")).click();
    	Thread.sleep(5000);
    	driver.findElement(AppiumBy.accessibilityId("Close tutorial")).click();
    	Thread.sleep(5000);
    	driver.switchTo().alert().accept();
    	Thread.sleep(5000);
    	driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.FrameLayout[1]")).click();
    	Thread.sleep(5000);
    	//driver.findElement(AppiumBy.accessibilityId("Graphics")).click(); 
    	collectAllElements();*/
    	
    	driver.terminateApp("de.hafas.android.standard4");
    	
    	Thread.sleep(5000);
    	//changeFontSize(driver, 1.5f);
    	if(changeFontSize(1.5f)) {
            // It's important to refresh the driver's context to ensure it recognizes the UI changes
        	
        	
    		
        	driver.activateApp("de.hafas.android.standard4");
        	
        	performChecksForScreen1("HomeScreenBig");
        	//navigateToSideDrawer();
        	//performChecksForScreen("NavigationDrawer");
        	navigateToTripPlannerPage();
        	performChecksForScreen1("TripPlannerBig");
        	navigateToDeparturesPage();
        	performChecksForScreen1("DeparturesBig");
        	navigateToMapSettingsPage();
        	performChecksForScreen1("MapSettingsBig");
        	navigateToLocationSearchPage();
        	performChecksForScreen1("LocationSearchBig");
        	navigateToStopsNearByPage();
        	performChecksForScreen1("StopsNearbyBig");
        	navigateToPOINearByPage();
        	performChecksForScreen1("POINearByBig");
        	navigateToNearByListPage();
        	performChecksForScreen1("NearByListBig");
        	navigateToMapMaterialPage();
        	performChecksForScreen1("MapMaterialBig");
        	navigateToAlarmPage();
        	performChecksForScreen1("AlarmsBig");
        	navigateToAlarmsHistoryPage();
        	performChecksForScreen1("AlarmsHistoryBig");
        	navigateToMyTripsPage();
        	performChecksForScreen1("MyTripsBig");
        	navigateToMyTripsEndedPage();
        	performChecksForScreen1("MyTripsEndedBig");
        	navigateToEventsPage();
        	performChecksForScreen1("EventsBig");
        	navigateToNewsPage();
        	performChecksForScreen1("NewsBig");
        	navigateToSettingsPage();
        	performChecksForScreen1("SettingsBig");
        	navigateToLegalNoticePage();
        	performChecksForScreen1("LegalNoticeBig");
        	navigateToNetworkMapsPage();
        	performChecksForScreen1("NetworkMapsBig");
        	
            // Re-collect and re-categorize elements after font size change
        	
            
             // Repeat the checks for the new font size
        }
    	
    	driver.terminateApp("de.hafas.android.standard4");
    	
    	
    	
    	
    	changeFontSize(1.0f); 
	    //String allElementsXpath = "";
	    
    	
    	//List<WebElement> currentScreenElements = driver.findElements(By.xpath("//*[contains(@class, 'android.widget')]"));
    	//System.out.println(currentScreenElements.size());
    	/*List<AccessibilityViolation> allViolations = new ArrayList<>();
    	
    	ElementCollections ec = collectAllElements(driver);
    	List<WebElement> allElements = ec.getAllElements();
		List<WebElement> linkElements =ec.getLinkElements();
    	
        Map<String, List<WebElement>> categorizedElements = categorizeElements(allElements,linkElements);
        allViolations= performAccessibilityChecks(categorizedElements,minWidth,minHeight,allViolations); 
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
         
         List<List<WebElement>> listOfLists = Arrays.asList(
        		 imageButtons, textViews,imageViews, buttons,linkElements,checkBoxes,radioButtons,toggleButtons,dropdowns
     		);
         
         List<WebElement> combinedList_contentdescription = combineElements(listOfLists);	
         
         List<List<WebElement>> listOfLists1 = Arrays.asList(
        		 imageButtons, textViews,imageViews, buttons,linkElements,checkBoxes,radioButtons,toggleButtons,inputFields,tabItems,sliders,switches,dropdowns
     		);
         
         List<WebElement> combinedList_touchTarget = combineElements(listOfLists1);

         List<List<WebElement>> listOfLists2 = Arrays.asList(
        		 imageButtons, textViews,imageViews, buttons,linkElements,checkBoxes,radioButtons,toggleButtons,inputFields,tabItems,sliders,switches,dropdowns
     		);
         
         List<WebElement> combinedList_Focusable = combineElements(listOfLists2);
         
         List<List<WebElement>> listOfLists3 = Arrays.asList(
        		  textViews, buttons,linkElements,checkBoxes,radioButtons,inputFields,tabItems
     		);
         
         List<WebElement> combinedList_checkVisibility = combineElements(listOfLists3);
         
         List<List<WebElement>> listOfLists4 = Arrays.asList(
        		 imageButtons, textViews, buttons,checkBoxes,radioButtons,switches, toggleButtons, sliders
    		);
        
        List<WebElement> combinedList_interactiveElement = combineElements(listOfLists4);
        
        List<List<WebElement>> listOfLists5 = Arrays.asList(
       		 imageButtons, textViews,imageViews, buttons,checkBoxes,radioButtons,linkElements,tabItems,switches, toggleButtons, sliders, dropdowns,inputFields
   		);
       
       List<WebElement> combinedList_Colorcontrast = combineElements(listOfLists5); */
         
         
         
    	/*if () {
            // It's important to refresh the driver's context to ensure it recognizes the UI changes
        	
        	
        	
        	driver.activateApp("de.hafas.android.standard4");
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        	
        	driver.findElement(By.id("de.hafas.android.standard4:id/button_tracking_optin_agree")).click();
        	Thread.sleep(5000);
        	driver.findElement(AppiumBy.accessibilityId("Close tutorial")).click();
        	Thread.sleep(5000);
        	driver.switchTo().alert().accept();
        	Thread.sleep(5000);
            // Re-collect and re-categorize elements after font size change
        	collectAllElements1();
            
             // Repeat the checks for the new font size
        }

        // Change font size to 0.9x and repeat checks
       if (changeFontSize(driver, 0.9f)) {
            // Refresh the driver's context again
        	driver.terminateApp("de.hafas.android.standard4");
        	
        	
        	driver.activateApp("de.hafas.android.standard4");
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        	
        	driver.findElement(By.id("de.hafas.android.standard4:id/button_tracking_optin_agree")).click();
        	Thread.sleep(5000);
        	driver.findElement(AppiumBy.accessibilityId("Close tutorial")).click();
        	Thread.sleep(5000);
        	driver.switchTo().alert().accept();
        	Thread.sleep(5000);
            // Re-collect and re-categorize elements after font size change
        	collectAllElements1();// Repeat the checks for the new font size
        }

        // Optionally, reset font size to default after tests
       
       if (!allViolations.isEmpty()) {
           generateAccessibilityReport(allViolations);
       }
         
         
         changeFontSize(driver, 1.0f); */
         
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
    																									


    