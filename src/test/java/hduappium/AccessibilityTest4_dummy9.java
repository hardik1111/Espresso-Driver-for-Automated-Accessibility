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


public class AccessibilityTest4_dummy9 extends Baseclass13 {
	
	List<AccessibilityViolation2> allViolations = new ArrayList<>();
	
	private boolean scrollForward(String scrollableUiSelector) {
	    try {
	        // Execute the scroll operation
	        driver.findElement(AppiumBy.androidUIAutomator(scrollableUiSelector + ".scrollForward()"));
	        Thread.sleep(1000); // Adjust based on your app's response time
	        return true;
	    } catch (NoSuchElementException | InterruptedException e) {
	        return false; // No more content to scroll to or scroll failed
	    }
	}

	private boolean isThereNewContent(String allElementsXpath, Set<String> previousSignatures) {
	    // Take a snapshot of the current state of elements
	    List<WebElement> elementsAfterScroll = driver.findElements(By.xpath(allElementsXpath));
	    Set<String> newSignatures = elementsAfterScroll.stream()
	            .map(element -> element.getLocation().toString() + "|" + element.getSize().toString())
	            .collect(Collectors.toSet());

	    // Check if there are any new signatures compared to the previous snapshot
	    newSignatures.removeAll(previousSignatures);
	    return !newSignatures.isEmpty();
	}

	private void collectAllElements() throws IOException, InterruptedException {
	    
	    int minWidth = 44;
	    int minHeight = 44;
	    String scrollableUiSelector = "new UiScrollable(new UiSelector().scrollable(true))";
	    //String allElementsXpath = "";
	    Set<String> previousSignatures = new HashSet<>();

	    while (true) {
	    	Thread.sleep(15000);
	        List<WebElement> currentScreenElements = driver.findElements(By.xpath("//*[contains(@class, 'android.widget')]"));
	        List<WebElement> currentScreenLinkElements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text, 'http://') or contains(@text, 'https://')]"));
	        Map<String, List<WebElement>> categorizedElements = categorizeElements(currentScreenElements,currentScreenLinkElements);
	        allViolations.addAll(performAccessibilityChecks(categorizedElements, minWidth, minHeight, allViolations));

	        // Update signatures before attempting to scroll
	        previousSignatures.addAll(currentScreenElements.stream()
	                .map(element -> element.getLocation().toString() + "|" + element.getSize().toString())
	                .collect(Collectors.toSet()));

	        if (!scrollForward(scrollableUiSelector) || !isThereNewContent("//*[contains(@class, 'android.widget')]", previousSignatures)) {
	            break; // Exit if unable to scroll further or no new content detected
	        }
	    }
	    
	    

	}
	
	private void collectAllElements1() throws IOException, InterruptedException {
	    
		int minWidth = 44;
	    int minHeight = 44;
	   
	    String scrollableUiSelector = "new UiScrollable(new UiSelector().scrollable(true))";
	    //String allElementsXpath = "";
	    Set<String> previousSignatures = new HashSet<>();

	    while (true) {
	    	Thread.sleep(15000);
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
		
		    Map<String, List<WebElement>> categorizedElements = new HashMap<>();
		    
		    
		    categorizedElements.put("contentDescription", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton", "android.widget.TextView","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner")));
		    List<WebElement> contentDescriptionElements = categorizedElements.computeIfAbsent("contentDescription", k -> new ArrayList<>());
		    contentDescriptionElements.addAll(linkElements);
		    
		    categorizedElements.put("touchTarget", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton", "android.widget.TextView","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","android.widget.SeekBar","android.widget.Switch", "android.widget.LinearLayout", "android.widget.FrameLayout", "android.widget.RelativeLayout")));
		    List<WebElement> touchTargetElements = categorizedElements.computeIfAbsent("touchTarget", k -> new ArrayList<>());
		    touchTargetElements.addAll(linkElements);
		    
		    categorizedElements.put("focusable", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton", "android.widget.TextView","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","android.widget.SeekBar","android.widget.Switch")));
		    List<WebElement> focusableElements = categorizedElements.computeIfAbsent("focusable", k -> new ArrayList<>());
		    focusableElements.addAll(linkElements);
		    
		    categorizedElements.put("checkVisibility", filterElementsBy(allElements, Arrays.asList("android.widget.TextView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.EditText","android.support.design.widget.TabLayout$TabView")));
		    List<WebElement> checkVisibilityElements = categorizedElements.computeIfAbsent("checkVisibility", k -> new ArrayList<>());
		    checkVisibilityElements.addAll(linkElements);
		    
		    categorizedElements.put("interactive", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton","android.widget.TextView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.SeekBar","android.widget.Switch")));
		    List<WebElement> interactiveElements = categorizedElements.computeIfAbsent("checkVisibility", k -> new ArrayList<>());
		    interactiveElements.addAll(linkElements);
		    
		    categorizedElements.put("colorContrastNonText", filterElementsBy(allElements, Arrays.asList("android.widget.ImageButton","android.widget.ImageView","android.widget.Button","android.widget.CheckBox","android.widget.RadioButton","android.widget.ToggleButton","android.widget.Spinner","android.widget.EditText","android.support.design.widget.TabLayout$TabView","android.widget.SeekBar","android.widget.Switch")));
		    List<WebElement> colorContrastNonTextElements = categorizedElements.computeIfAbsent("colorContrast", k -> new ArrayList<>());
		    colorContrastNonTextElements.addAll(linkElements);
		    
		    categorizedElements.put("colorContrastText", filterElementsBy(allElements, Arrays.asList("android.widget.TextView")));
		    List<WebElement> colorContrastTextElements = categorizedElements.computeIfAbsent("colorContrast", k -> new ArrayList<>());
		    colorContrastTextElements.addAll(linkElements);

		    categorizedElements.put("textView", filterElementsBy(allElements, Arrays.asList("android.widget.TextView")));
		    
		    return categorizedElements;
		}
	

	private List<WebElement> filterElementsBy(List<WebElement> elements, List<String> classNames) {
	    return elements.stream()
	            .filter(e -> classNames.contains(e.getAttribute("className")))
	            .collect(Collectors.toList());
	}
	
	
	
	private List<AccessibilityViolation2> performAccessibilityChecks(Map<String, List<WebElement>> categorizedElements, int minWidth, int minHeight,List<AccessibilityViolation2> allViolations) throws IOException {
	    // Your existing logic to perform checks on the categorized lists
	    // Example: allViolations.addAll(checkContentDescription(categorizedElements.get("contentDescription")));
		
		//allViolations.addAll(checkContentDescription(categorizedElements.get("contentDescription")));
        allViolations.addAll(checkTouchTargetSize(categorizedElements.get("touchTarget"), minWidth, minHeight));
        //allViolations.addAll(checkFocusability(categorizedElements.get("focusable")));
        //allViolations.addAll(checkTextVisibilityAndReadability(categorizedElements.get("checkVisibility")));
        //allViolations.addAll(checkInteractiveElementStates(categorizedElements.get("interactive")));
        //allViolations.addAll(checkColorContrastNonText(categorizedElements.get("colorContrastNonText")));
        //allViolations.addAll(checkColorContrastText(categorizedElements.get("colorContrastText")));
        
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
    	Thread.sleep(5000);
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

	

	
	@Test
	public void accessibilityTest() throws InterruptedException, IOException
    {
        
    	
    	
    	System.out.println("Drive name is: "+driver);
    	performChecksForScreen("InitialScreen");
    	navigateToTutorialPage();
    	performChecksForScreen("Turorial");
    	navigateToHomePage();
    	performChecksForScreen("HomeScreen");
    	/*//navigateToSideDrawer();
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
    	performChecksForScreen("Alarms");
    	navigateToAlarmsHistoryPage();
    	performChecksForScreen("AlarmsHistory"); */
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
    	collectAllElements();
    	
    	driver.terminateApp("de.hafas.android.standard4");
    	
    	Thread.sleep(5000);
    	//changeFontSize(driver, 1.5f);
    	if(changeFontSize(driver, 1.5f)) {
            // It's important to refresh the driver's context to ensure it recognizes the UI changes
        	
        	
    		
        	driver.activateApp("de.hafas.android.standard4");
        	driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.FrameLayout[1]")).click();
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        	
        	
            // Re-collect and re-categorize elements after font size change
        	collectAllElements1();
            
             // Repeat the checks for the new font size
        }
    	
    	driver.terminateApp("de.hafas.android.standard4");
    	
    	Thread.sleep(5000);
    	
    	if(changeFontSize(driver, 0.9f)) {
            // It's important to refresh the driver's context to ensure it recognizes the UI changes
        	
        	
    		
        	driver.activateApp("de.hafas.android.standard4");
        	driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.FrameLayout[1]")).click();
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        	
        	
            // Re-collect and re-categorize elements after font size change
        	collectAllElements1();
            
             // Repeat the checks for the new font size
        }
    	
    	
    	if (!allViolations.isEmpty()) {
    		generatePdfReport(allViolations);
        }
    	changeFontSize(driver, 1.0f); 
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
    																									


    