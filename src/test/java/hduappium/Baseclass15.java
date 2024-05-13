package hduappium;

import java.awt.Color;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.apache.poi.util.IOUtils;
import java.io.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

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

public class Baseclass15 {

	public AndroidDriver driver;
	public AppiumDriverLocalService appiumService;
	private LocalDateTime timestamp;
	
	static { System.load("C:/Users/hdu/eclipse/Espresso_Integration/opencv/build/java/x64/opencv_java490.dll"); }
	
	public void ConfigureAppium() throws MalformedURLException
	{
		
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe")) // Path to Node.js executable on Windows
                .withAppiumJS(new File("C:\\Users\\hdu\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")) // Path to Appium's main.js file on Windows
                .withLogFile(new File("appium.log")) // Optional: Set log file path
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE) 
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info"); // Optional: Set log level

        
        appiumService = AppiumDriverLocalService.buildService(builder);
        appiumService.start();
			
								
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName("Pixel 6 API 33"); //emulator
			//options.setDeviceName("Android Device");// real device
			
			ChromeOptions chromeOptions1 = new ChromeOptions();
		    chromeOptions1.addArguments("--disable-popup-blocking");
		    chromeOptions1.addArguments("--disable-infobars");
		    chromeOptions1.addArguments("--disable-notifications");
			
			//options.setChromedriverExecutable("C://Users//hdu//Downloads//chromedriver_win32");
			options.setCapability("chromedriverExecutable", "C:\\Users\\hdu\\Downloads\\chromedriver.exe");
			
			options.setCapability("chromeOptions", chromeOptions1.toJson());
			options.setApp("C://Users//hdu//eclipse-workspace//Appium//src//test//java//resources//ApiDemos-debug.apk");	
		//	options.setApp("//Users//rahulshetty//workingcode//Appium//src//test//java//resources//General-Store.apk");
			
			 driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@BeforeClass
	public void ConfigureAppium1() throws MalformedURLException 
	{
		
		    // Set up the Appium service configuration
		    AppiumServiceBuilder builder = new AppiumServiceBuilder();
		    builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe")) // Path to Node.js executable
		           .withAppiumJS(new File("C:\\Users\\hdu\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")) // Path to Appium main.js file
		           .withLogFile(new File("appium.log")) // Specify log file for Appium
		           .withArgument(GeneralServerFlag.LOCAL_TIMEZONE) // Use local timezone for server
		           .withArgument(GeneralServerFlag.LOG_LEVEL, "debug"); // Set log level to debug for detailed logging

		    // Build and start the Appium service
		    appiumService = AppiumDriverLocalService.buildService(builder);
		    appiumService.start();

		    // Define desired capabilities for the Android driver
		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability("platformName", "Android"); // Specify OS type
		    caps.setCapability("deviceName", "motorola motorola edge 30 Pro"); // Specify device name (or emulator)
		    caps.setCapability("udid", "ZY22F7M9N8");
		    caps.setCapability("automationName", "UiAutomator2"); // Automation engine
		    caps.setCapability("app", "C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/resources/standard-366-standard4-prod.apk"); // Path to app to test
		    caps.setCapability("appPackage", "de.hafas.android.standard4"); // App package name
		    caps.setCapability("appActivity", "de.hafas.main.HafasApp"); // App activity to be launched
		    caps.setCapability("enforceXPath1", true); // Force use of XPath1.0 standards for compatibility

		    // Initialize Android driver with specified capabilities and server URL
		    driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
		    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Set implicit wait to handle element delays
		
        //resetFontSizeToDefault();
  }
	

	
	public static int getDeviceDpi() throws IOException, NumberFormatException {
		// Execute the adb command to get the device screen density
        Process process = Runtime.getRuntime().exec("adb shell wm density");
        // Set up a BufferedReader to read the process's output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        int dpi = 0;
        // Read the output from the command line by line
        while ((line = reader.readLine()) != null) {
        	// Check if the line contains the expected "Physical density:" text
            if (line.contains("Physical density:")) {
            	// Extract the numeric DPI value from the string and convert it to an integer
                dpi = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                break; // Exit the loop once the DPI is found
            } 
        }
     // Close the reader to free up system resources
        reader.close();
        
     // Wait for the process to complete its execution and check for any interruptions
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Re-interrupt the thread to handle the interrupt properly
            throw new IOException("Interrupted while waiting for the dpi command to complete.", e);
        }
     // Check if the DPI was not found and throw an exception if true
        if (dpi == 0) {
            throw new IOException("Failed to retrieve device DPI. ADB command might not have executed properly.");
        }

        return dpi; // Return the retrieved DPI value
    }
	
	
	// Convert pixel measurements to density-independent pixels (dp)
	private static int convertPixelsToDp(int pixels, int dpi) {
		// Divide pixels by the density factor (dpi / 160) to convert to dp
        return (int) (pixels / (dpi / 160));
    }
	// Get the size of a web element in dp units
    public static Dimension getSizeInDp(WebElement element, int dpi) {
        Dimension sizeInPixels = element.getSize(); // Retrieve size of the element in pixels	
        int widthInDp = convertPixelsToDp(sizeInPixels.getWidth(), dpi);// Convert the width from pixels to dp
        int heightInDp = convertPixelsToDp(sizeInPixels.getHeight(), dpi);// Convert the height from pixels to dp
        return new Dimension(widthInDp, heightInDp); // Return the size of the element in dp as a new Dimension object
    }
	
    public List<AccessibilityViolation2> checkContentDescription(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType="Missing Content Description";
        int i=0; // Counter for naming screenshot files uniquely
        for (WebElement element : elements) {
        	
        	// Retrieve various attributes to determine the state and type of the element
        	String className = element.getAttribute("class");
            boolean isClickable = Boolean.parseBoolean(element.getAttribute("clickable"));
            boolean isCheckable = Boolean.parseBoolean(element.getAttribute("checkable"));
            boolean isEnabled = element.isEnabled();
            String focusable = element.getAttribute("focusable");
            String contentDesc = element.getAttribute("content-desc");
            String text = element.getAttribute("text");
            boolean isSelected = Boolean.parseBoolean(element.getAttribute("selected"));
            boolean hasContentDescription = (contentDesc != null && !contentDesc.isEmpty());
            boolean hasText = (text != null && !text.isEmpty());

            // Check clickable and checkable elements that are not specific layouts for missing content descriptions or text
            if ((isClickable || isCheckable) && (!className.equals("android.widget.RelativeLayout") && !className.equals("android.widget.FrameLayout") && !className.equals("android.widget.LinearLayout")) && (!hasContentDescription && !hasText)) {
                i++;
                String screenshotPath = takeElementScreenshot(element, i, violationType);
                violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Clickable element should have Content Description or Text"));
            }

         // Check specific layouts when clickable for missing content descriptions
            if (isClickable && (className.equals("android.widget.RelativeLayout") || className.equals("android.widget.FrameLayout") || className.equals("android.widget.LinearLayout"))) {
                if (!hasContentDescription) {
                	List<WebElement> childTextElements = new ArrayList<>();
                    // If it's a layout without content-description, check for TextView child with text
                    List<WebElement> childTextViews = element.findElements(By.className("android.widget.TextView"));
                    List<WebElement> childEditTextViews = element.findElements(By.className("android.widget.EditText"));
                    List<WebElement> childCheckBoxViews = element.findElements(By.className("android.widget.CheckBox"));
                    childTextElements.addAll(childTextViews);
                    childTextElements.addAll(childEditTextViews);
                    childTextElements.addAll(childCheckBoxViews);
                    if (childTextElements.isEmpty())
                    {
                    	i++;
                        String screenshotPath = takeElementScreenshot(element, i, violationType);
                        violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Clickable element should have Content Description or Text"));
                    }
                    else
                    {
                    boolean childHasText = childTextElements.stream().anyMatch(e -> e.getAttribute("text") != null && !e.getAttribute("text").isEmpty());

                    if (!childHasText) {
                        i++;
                        String screenshotPath = takeElementScreenshot(element, i, violationType);
                        violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Layout element should have Content Description or a TextView child with Text"));
                    }
                    }
                }
            } 
         // Check non-clickable, selected LinearLayouts for missing content descriptions
            if (!isClickable && className.equals("android.widget.LinearLayout") && isSelected) {
                if (!hasContentDescription) {
                	List<WebElement> childTextElements = new ArrayList<>();
                    // If it's a layout without content-description, check for TextView child with text
                    List<WebElement> childTextViews = element.findElements(By.className("android.widget.TextView"));
                    List<WebElement> childEditTextViews = element.findElements(By.className("android.widget.EditText"));
                    List<WebElement> childCheckBoxViews = element.findElements(By.className("android.widget.CheckBox"));
                    childTextElements.addAll(childTextViews);
                    childTextElements.addAll(childEditTextViews);
                    childTextElements.addAll(childCheckBoxViews);
                    
                    if (childTextElements.isEmpty())
                    {
                    	i++;
                        String screenshotPath = takeElementScreenshot(element, i, violationType);
                        violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Non Clickable but selected element should have Content Description or Text"));
                    }
                    else 
                    {
                    	 boolean childHasText = childTextElements.stream().anyMatch(e -> e.getAttribute("text") != null && !e.getAttribute("text").isEmpty());

                         if (!childHasText) {
                             i++;
                             String screenshotPath = takeElementScreenshot(element, i, violationType);
                             violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Layout element should have Content Description or a TextView child with Text"));
                         }
                    }
                    	
                   
                }
            } 
            
            if (!isClickable && className.equals("android.widget.LinearLayout") && isSelected) {
                if (!hasContentDescription) {
                	List<WebElement> childTextElements = new ArrayList<>();
                    // If it's a layout without content-description, check for TextView child with text
                    List<WebElement> childTextViews = element.findElements(By.className("android.widget.TextView"));
                    List<WebElement> childEditTextViews = element.findElements(By.className("android.widget.EditText"));
                    childTextElements.addAll(childTextViews);
                    childTextElements.addAll(childEditTextViews);
                    
                    if (childTextElements.isEmpty())
                    {
                    	i++;
                        String screenshotPath = takeElementScreenshot(element, i, violationType);
                        violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Non Clickable but selected element should have Content Description or Text"));
                    }
                    else 
                    {
                    	 boolean childHasText = childTextElements.stream().anyMatch(e -> e.getAttribute("text") != null && !e.getAttribute("text").isEmpty());

                         if (!childHasText) {
                             i++;
                             String screenshotPath = takeElementScreenshot(element, i, violationType);
                             violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Layout element should have Content Description or a TextView child with Text"));
                         }
                    }
                    	
                   
                }
            } 
         // Ensure non-clickable TextViews contain text
            if (className.equals("android.widget.TextView") && !isClickable) {
                // Non-clickable TextViews should have text
                if (!hasText) {
                    i++;
                    String screenshotPath = takeElementScreenshot(element, i, violationType);
                    violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Non-clickable TextView should have Text"));
                }
            }
        }
        return violations; // Return list of identified violations
    }

    public List<AccessibilityViolation2> checkTouchTargetSize(List<WebElement> elements, int minWidth, int minHeight) throws NumberFormatException, IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>(); // Create an empty list to store accessibility violations
        String violationType = "Inadequate Touch Target Size"; // Define the type of violation we are checking for
        int dpi =  getDeviceDpi(); // Get the device's DPI to calculate dimensions in density-independent pixels (dp)
        int i=0; // Counter for indexing screenshots
        
        // Iterate over each element in the provided list
        for (WebElement element : elements) {
        	 boolean isClickable = Boolean.parseBoolean(element.getAttribute("clickable"));
        	
        	if(isClickable) { // Process only clickable elements
               
        	
            Dimension size = getSizeInDp(element,dpi); // Get the size of the element in dp
            if ((size.width < minWidth) || (size.height < minHeight)) { // Check if the element's size is below the minimum required
            	i++; // Increment screenshot index
            	String screenshotPath = takeElementScreenshot(element,i,violationType); // Take a screenshot of the element
            	String details = "Actual Size: " + size.width + "x" + size.height + "dp, Required Minimum: " + minWidth + "x" + minHeight + "dp"; // Details about the violation
                violations.add(new AccessibilityViolation2(screenshotPath,violationType , details)); // Add the new violation to the list
            }
        	}
        }
        return violations; // Return the list of found violations
    }
    
    public List<AccessibilityViolation2> checkFocusability(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>(); // Initialize a list to hold any found accessibility violations
        String violationType = "Element Not Focusable"; // Define the type of violation to check for
        int i=0; // Counter for naming screenshot files uniquely
        
     // Iterate over each element in the provided list
        for (WebElement element : elements) {
        	// Check if the element is meant to be clickable
        if (element.getAttribute("clickable").equalsIgnoreCase("true")) {
        	String focusable = element.getAttribute("focusable"); // Retrieve the 'focusable' attribute of the element
        	// Check if the 'focusable' attribute is missing or not set to 'true'
            if (focusable == null || !focusable.equalsIgnoreCase("true")) {
            	i++; // Increment the screenshot file counter
            	// Take a screenshot of the element, naming it based on the violation type and count
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
            	
            	// Add a new violation to the list with details about the issue
                violations.add(new AccessibilityViolation2(screenshotPath,violationType, "This element should be focusable"));
            }
          }
        }

        return violations; // Return the list of found violations
    }
    
   
    
    public List<AccessibilityViolation2> checkVisibilityAndReadability(List<WebElement> elements) throws IOException {
    	// Initialize a list to hold any found accessibility violations
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Visibility issue found!"; // Define violation message for visibility issues
        String violationType1 = "Readability issue found!"; // Define violation message for readability issues
        // Counters for numbering screenshots
        int i=0;
        int j=0;
        
        // Iterate over each element provided in the list
        for (WebElement element : elements) {
        	// Retrieve the class attribute of the element to identify its type
        	String elementType = element.getAttribute("class");
        	// Check if the element is not displayed on the screen
            if (!element.isDisplayed()) {
            	i++; // Increment counter for visibility issues
            	// Take a screenshot of the element and retrieve the path
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
            	// Add a new violation for visibility issues
                violations.add(new AccessibilityViolation2(screenshotPath,violationType ,"Element's isDisplayed attribute should be true"));
                continue; // Skip further checks and move to the next element
            }
            // Check if the element is one of the specified types that should contain text
            if (elementType.matches("android.widget.TextView|android.widget.Button|android.widget.EditText")) {
            	// Get the text of the element
            String text = element.getText();
            String contentDesc = element.getAttribute("content-desc");
         // Check if the text is null or effectively empty
            if ((text == null || text.trim().isEmpty()) && (contentDesc == null || contentDesc.trim().isEmpty())) {
            	j++; // Increment counter for readability issues
            	// Take a screenshot of the element and retrieve the path
            	String screenshotPath = takeElementScreenshot(element,j,violationType1);
            	// Add a new violation for readability issues
                violations.add(new AccessibilityViolation2(screenshotPath, violationType1, "The text should not empty or whitespace."));
            }
            }
        }
        return violations; // Return the list of accessibility violations found
    }
    
    public List<AccessibilityViolation2> checkInteractiveElementStates(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Element Disabled"; // Defines the type of accessibility violation being checked
        int i=0; // Counter for naming screenshots uniquely
        
        // Iterate over each WebElement passed to the method
        for (WebElement element : elements) {
            boolean isEnabled = element.isEnabled(); // Check if the element is enabled 
            boolean isDisplayed = element.isDisplayed();
            boolean isClickable = Boolean.parseBoolean(element.getAttribute("clickable"));// Check if the element is displayed
            
         // Check condition where the element is displayed but not enabled
            if (isClickable && isDisplayed && !isEnabled) {
            	i++;// Increment the counter for each violation found
            	// Take a screenshot of the element in its current state
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
            	// Add a new violation to the list with details of the issue
                violations.add(new AccessibilityViolation2(
                		screenshotPath,
                	violationType,
                    "This interactive element should be displayed and enabled."));
            }
        }
        return violations; // Return the list of found accessibility violations
    }
    
    public List<AccessibilityViolation2> checkTextScalingAdaptation(List<WebElement> textElements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>(); // List to store accessibility violations found during the check
        String violationType = "Text Not Visible after Textscaling"; // Define the type of violation related to visibility issues
        String violationType1 = "Invalid Text Dimensions after Textscaling"; // Define the type of violation related to dimension issues
        int i=0; // Counter for indexing screenshots when text is not visible
        int j=0; // Counter for indexing screenshots when text has invalid dimensions
     // Iterate over all provided text elements to check for accessibility issues
        for (WebElement textElement : textElements) {
            if (!textElement.isDisplayed()) {   // Check if the text element is not displayed
            	i++; // Increment visibility issue counter	
            	// Take a screenshot of the element, store it, and get the path
            	String screenshotPath = takeElementScreenshot(textElement,i,violationType);
            	// Add a new violation for not being visible at current text scaling
                violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Text element should be visible at current text scaling."));
                continue; // Move to the next element
            }

            Dimension size = textElement.getSize();  // Get the size of the text element
            if (size.height == 0 || size.width == 0) { // Check if the dimensions of the element are zero
            	j++; // Increment dimension issue counter
            	 // Take a screenshot of the element, store it, and get the path
            	String screenshotPath = takeElementScreenshot(textElement,j,violationType1);
            	// Add a new violation for having invalid dimensions at current text scaling
                violations.add(new AccessibilityViolation2(screenshotPath, violationType1, "Text element should have valid dimensions at current text scaling."));
            }
        }

        return violations; // Return the list of all collected accessibility violations
  }
    public List<AccessibilityViolation2> checkColorContrastText(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        File screenshot = driver.getScreenshotAs(OutputType.FILE); // Take a screenshot of the current page
        BufferedImage fullImg = ImageIO.read(screenshot); // Convert the screenshot file into a BufferedImage for processing
        String violationType = "Insufficient Color Contrast Text"; // Define a constant for the type of violation to be checked
        int i=0; // Counter to keep track of elements processed, used in screenshot naming
        
        // Iterate over all provided web elements
        for (WebElement element : elements) {
        	
        	Point point = element.getLocation(); // Get the location and size of the element
        	int elementWidth = element.getSize().getWidth();
        	int elementHeight = element.getSize().getHeight();
        	// Crop the screenshot to just the element's area
        	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        	// Specify the file path where the element's screenshot will be saved
        	File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png");
        	try {
        		// Save the cropped image as a PNG
        	    ImageIO.write(elementScreenshot, "png", outputFile);
        	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
        	} catch (IOException e) {
        	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
        	}
        	// Find the two most dominant colors in the screenshot
        	List<Color> colors= findDominantColors("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png", 2);
        	if (colors.size() >= 2) {
        	    Color mostDominantColor = colors.get(0);
        	    Color secondMostDominantColor = colors.get(1);
        	    // Calculate the contrast ratio between the two most dominant colors
        	    double contrastRatio = calculateContrastRatio(mostDominantColor, secondMostDominantColor);
        	    // Check if the contrast ratio is below the accessibility minimum of 4.5
        	    if (contrastRatio < 4.5) {
            		i++;
            		// Take a screenshot of the element that violates the contrast rule
            		String screenshotPath = takeElementScreenshot(element,i,violationType);
            		// Add a new violation to the list with details about the issue
                    violations.add(new AccessibilityViolation2(
                    	screenshotPath,
                        violationType,
                        "Contrast ratio:"+contrastRatio+" should be at least 4.5"
                    ));
                }
        	}
        }

        return violations; // Return the list of violations found
    }
    
    public List<AccessibilityViolation2> checkColorContrastNonText(List<WebElement> elements) throws IOException {
    	
            List<AccessibilityViolation2> violations = new ArrayList<>();
            File screenshot = driver.getScreenshotAs(OutputType.FILE); // Take a screenshot of the current page
            BufferedImage fullImg = ImageIO.read(screenshot); // Convert the screenshot file into a BufferedImage for processing
            String violationType = "Insufficient Color Contrast Non-Text"; // Define a constant for the type of violation to be checked
            int i=0; // Counter to keep track of elements processed, used in screenshot naming
            
            // Iterate over all provided web elements
            for (WebElement element : elements) {
            	
            	Point point = element.getLocation(); // Get the location and size of the element
            	int elementWidth = element.getSize().getWidth();
            	int elementHeight = element.getSize().getHeight();
            	// Crop the screenshot to just the element's area
            	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
            	// Specify the file path where the element's screenshot will be saved
            	File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png");
            	try {
            		// Save the cropped image as a PNG
            	    ImageIO.write(elementScreenshot, "png", outputFile);
            	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
            	} catch (IOException e) {
            	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
            	}
            	// Find the two most dominant colors in the screenshot
            	List<Color> colors= findDominantColors("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png", 2);
            	if (colors.size() >= 2) {
            	    Color mostDominantColor = colors.get(0);
            	    Color secondMostDominantColor = colors.get(1);
            	    // Calculate the contrast ratio between the two most dominant colors
            	    double contrastRatio = calculateContrastRatioNonText(mostDominantColor, secondMostDominantColor);
            	    // Check if the contrast ratio is below the accessibility minimum of 3
            	    if (contrastRatio < 3) {
                		i++;
                		// Take a screenshot of the element that violates the contrast rule
                		String screenshotPath = takeElementScreenshot(element,i,violationType);
                		// Add a new violation to the list with details about the issue
                        violations.add(new AccessibilityViolation2(
                        	screenshotPath,
                            violationType,
                            "Contrast ratio:"+contrastRatio+" should be at least 3"
                        ));
                    }
            	}
            }

            return violations; // Return the list of violations found
    }
    
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
    
    public boolean changeFontSize(float scale) {
        try {
            Runtime.getRuntime().exec(String.format("adb shell settings put system font_scale %s", scale));
            Thread.sleep(1000); // Give the system time to apply the change.

            // Optionally, verify the change was applied as in your original method.
            // This may involve pulling the setting value with another adb shell command and parsing the output.

            return true; // Indicate success, or modify based on verification results.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // Indicate failure.
        }
    }
    
    public boolean setEmulatorLocation(double longitude, double latitude) {
        try {
            // Format and execute the adb command to set emulator location
            String command = String.format("adb emu geo fix %f %f", longitude, latitude);
            Runtime.getRuntime().exec(command);
            
            // Give the system time to apply the location change
            Thread.sleep(1000);
            
            // Optionally, add verification code here to check if the location was set correctly
            // This might involve pulling the location back via adb and comparing it to the input values

            return true; // Indicate success, or modify based on verification results.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // Indicate failure due to an exception.
        }
    }

    
    public void resetFontSizeToDefault() {
        try {
            // Command to reset the font size to default (1.0)
            String resetCommand = "settings put system font_scale 1.0";
            driver.executeScript("mobile: shell", ImmutableMap.of("command", resetCommand));

            // Optional: Add a delay to allow the system to apply the change
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to reset font size to default: " + e.getMessage());
            // Handle the exception as needed
        }
    }
    
    public void generateAccessibilityReport1(List<AccessibilityViolation2> violations) {
        String fileName = "C:\\Users\\hdu\\eclipse\\Espresso_Integration\\src\\test\\java\\Testresult\\accessibility_violations_report.xlsx";
        
        
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Accessibility Violations");
            Drawing<?> drawing = sheet.createDrawingPatriarch();

            // Create a header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Element Screenshot");
            headerRow.createCell(1).setCellValue("Violation Type");
            headerRow.createCell(2).setCellValue("Details");

            // Fill data
            int rowNum = 1;
            for (AccessibilityViolation2 violation : violations) {
                int imageColumn = 0;
                Row row = sheet.createRow(rowNum);
                // Assuming the screenshot path is stored in `elementDescription`
                String screenshotPath = violation.getElementDescription();
                InputStream is = new FileInputStream(screenshotPath);
                byte[] bytes = IOUtils.toByteArray(is);
                int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                is.close();

                CreationHelper helper = workbook.getCreationHelper();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(imageColumn);
                anchor.setRow1(rowNum); // Same row as text cells
                Picture pict = drawing.createPicture(anchor, pictureIdx);
                pict.resize(); // Resize image to fit the cell

                row.createCell(1).setCellValue(violation.getViolationType());
                row.createCell(2).setCellValue(violation.getAdditionalDetails());

                rowNum++;
            }

            // Autosize columns for text
            for (int i = 1; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to the file
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            
            System.out.println("Accessibility report generated: " + fileName);

        } catch (IOException e) {
            System.err.println("Error writing to XLSX file: " + e.getMessage());
        }
    }
    
    
    public void generateAccessibilityReport2(List<AccessibilityViolation2> violations) {
        String fileName = "C:\\Users\\hdu\\eclipse\\Espresso_Integration\\src\\test\\java\\Testresult\\accessibility_violations_report.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Accessibility Violations");
            Drawing<?> drawing = sheet.createDrawingPatriarch();

            // Create a header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Screenshot");
            headerRow.createCell(1).setCellValue("Violation Type");
            headerRow.createCell(2).setCellValue("Details");

            int rowNum = 1;
            for (AccessibilityViolation2 violation : violations) {
                String imagePath = violation.getElementDescription();
                File imageFile = new File(imagePath);

                // Skip the violation entry if the screenshot file does not exist
                if (!imageFile.exists()) {
                    continue;
                }

                int imageColumn = 0;
                Row row = sheet.createRow(rowNum);

                // Add the image
                try (InputStream is = new FileInputStream(imageFile)) {
                    byte[] bytes = IOUtils.toByteArray(is);
                    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                    ClientAnchor anchor = new XSSFClientAnchor();
                    anchor.setCol1(imageColumn);
                    anchor.setRow1(rowNum); // Same row as the violation data
                    anchor.setCol2(imageColumn + 1);
                    anchor.setRow2(rowNum + 1); // Image height: spans across one row, adjust as needed
                    Picture pict = drawing.createPicture(anchor, pictureIdx);
                    pict.resize(); // Auto-size the image within the anchor bounds
                } catch (IOException e) {
                    System.err.println("Error adding screenshot: " + e.getMessage());
                }

                // Add text data
                row.createCell(1).setCellValue(violation.getViolationType());
                row.createCell(2).setCellValue(violation.getAdditionalDetails());

                rowNum++;
            }

            // Autosize columns for text data, skipping the screenshot column
            for (int i = 1; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to the file
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }

            System.out.println("Accessibility report generated: " + fileName);

        } catch (IOException e) {
            System.err.println("Error writing to XLSX file: " + e.getMessage());
        }
    }
    
    public void generatePdfReport(List<AccessibilityViolation2> violations, String ScreenName) {
        PdfWriter writer = null;
        // Define the file path and name for the PDF report, incorporating the screen name.
        String fileName = "C:\\Users\\hdu\\eclipse\\Espresso_Integration\\src\\test\\java\\Testresult\\accessibility_violations_report"+ScreenName+".pdf";
        // Set to store unique identifiers to avoid duplicate entries.
        Set<String> uniqueEntries = new HashSet<>();
        
        try {
        	// Attempt to initialize the PdfWriter with the specified file path.
            writer = new PdfWriter(fileName);
        } catch (FileNotFoundException e) {
        	// Print stack trace if the file is not found and exit the method.
            e.printStackTrace();
            return;
        }
        
        // Create a PdfDocument with the writer, which will write content to the file.
        PdfDocument pdf = new PdfDocument(writer);
        // Document object to add elements to the PDF.
        Document document = new Document(pdf);
        
        // Iterate over each accessibility violation passed to the method.
        for (AccessibilityViolation2 violation : violations) {
        	// Get the image path from the violation details.
            String imagePath = violation.getElementDescription();
            // Create a unique identifier for the current entry to manage duplicates.
            String uniqueIdentifier = imagePath + "_" + violation.getViolationType() + "_" + violation.getAdditionalDetails();
            
            // Continue to next iteration if the entry is a duplicate.
            if (!uniqueEntries.add(uniqueIdentifier)) continue;
            // Check if the screenshot file exists.
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) continue;

            // Attempt to add the screenshot to the document.
            try {
                ImageData data = ImageDataFactory.create(imagePath);
                Image img = new Image(data);
                document.add(img);
            } catch (MalformedURLException e) {
            	// Print stack trace if the image URL is malformed
                e.printStackTrace();
            }

            // Add text describing the violation type.
            document.add(new Paragraph("Violation Type: " + violation.getViolationType()));
            // Add text providing additional details about the violation.
            document.add(new Paragraph("Details: " + violation.getAdditionalDetails()));

            // Add a separator line for better readability between entries.
            document.add(new Paragraph("\n---------------------------\n"));
        }
        // Close the document to finalize the PDF.
        document.close();
        // Output to console the location of the generated PDF report.
        System.out.println("PDF report generated: " + fileName);
    }
	
	public Double getFormattedAmount(String amount)
	{
		Double price = Double.parseDouble(amount.substring(1));
		return price;
		
	}
	
	public String takeElementScreenshot(WebElement element,int count,String violationType) throws IOException {
		// Capture the entire screen as a file
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
	    BufferedImage fullImg = ImageIO.read(screen); // Read the image from the file

	    // Get the location of element on the page
	    Point point = element.getLocation();

	    // Get the dimensions of the element
	    int elementWidth = element.getSize().getWidth();
	    int elementHeight = element.getSize().getHeight();

	    // Crop the screenshot to get only the element
	    BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
	    // Further processing to crop the image as necessary
	    BufferedImage croppedImage = getCroppedImage(elementScreenshot);
	    // Generate a formatted timestamp for the filename
	    String time = getFormattedTimestamp();
	    // Create a file object to save the screenshot
		File fileName = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Violation-SS/"+violationType+"_"+time+"_"+count+".png");
		
		try {
			// Write the cropped image to file in PNG format
    	    ImageIO.write(croppedImage, "png", fileName);
    	    System.out.println("Saved element screenshot to " + fileName.getAbsolutePath());
    	} catch (IOException e) {
    		// Handle possible I/O errors in saving the file
    	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
    	}
		// Return the path where the screenshot was saved
		return fileName.getAbsolutePath();
	    
}
	
	public String getFormattedTimestamp() {
		// Get the current date and time
		this.timestamp = LocalDateTime.now();
		// Define the date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        // Format the current date and time and return it
        return ((LocalDateTime) this.timestamp).format(formatter);
 }
	
	@AfterClass
	public void tearDown()
	{
		driver.quit(); // Stops and closes the WebDriver session.
		appiumService.stop(); // Stops the Appium server.
	}
	
	// Method to linearize color component value based on the sRGB standard.
	private double linearize(double value) {
		// Linearization is done by adjusting the gamma curve of sRGB colors.
	    return (value <= 0.03928) ? value / 12.92 : Math.pow((value + 0.055) / 1.055, 2.4);
	}

	// Calculates the relative luminance of a color in the sRGB color space.
	private double getLuminance(Color color) {
		// Linearize each component of the color.
	    double r = linearize(color.getRed() / 255.0); // Red component linearized.
	    double g = linearize(color.getGreen() / 255.0); // Green component linearized.
	    double b = linearize(color.getBlue() / 255.0); // Blue component linearized.
	    // Apply the formula for relative luminance.
	    return 0.2126 * r + 0.7152 * g + 0.0722 * b; // Weighted sum of RGB components.
	}

	// Calculates the contrast ratio between two colors, suitable for determining text legibility
	private double calculateContrastRatio(Color background, Color foreground) {
		// Obtain the luminance of both the foreground and background colors
	    double luminance1 = getLuminance(foreground) + 0.05;
	    double luminance2 = getLuminance(background) + 0.05;
	    // Return the contrast ratio, which is the ratio of the luminances.
	    return (luminance1 > luminance2) ? (luminance1 / luminance2) : (luminance2 / luminance1);
	} 
	
	// This method is a duplication of calculateContrastRatio. It might be used for non-text elements with a different set of accessibility criteria.
	private double calculateContrastRatioNonText(Color background, Color foreground) {
	    double luminance1 = getLuminance(foreground) + 0.05;
	    double luminance2 = getLuminance(background) + 0.05;
	    return (luminance1 > luminance2) ? (luminance1 / luminance2) : (luminance2 / luminance1);
	} 
	
private BufferedImage getCroppedImage(BufferedImage image)
	{
		// Variables to track the minimum and maximum bounds of the non-white area
		int minX = Integer.MAX_VALUE;
	    int minY = Integer.MAX_VALUE;
	    int maxX = 0;
	    int maxY = 0;

	    // Iterate over every pixel in the image to find the bounds of non-white pixels
	    for (int x = 0; x < image.getWidth(); x++) {
	        for (int y = 0; y < image.getHeight(); y++) {
	        	// Check if the current pixel is not white
	            if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
	            	// Update the boundaries for non-white pixels
	                minX = Math.min(minX, x);
	                maxX = Math.max(maxX, x);
	                minY = Math.min(minY, y);
	                maxY = Math.max(maxY, y);
	            }
	        }
	    }

	    // Correct the boundaries to ensure they are within the image's dimensions
	    minX = Math.max(0, minX);
	    minY = Math.max(0, minY);
	    maxX = Math.min(image.getWidth() - 1, maxX);
	    maxY = Math.min(image.getHeight() - 1, maxY);

	    // Compute the dimensions of the cropped area
	    int safeWidth = maxX - minX + 1;
	    int safeHeight = maxY - minY + 1;

	    // If dimensions are zero or negative, the image may be entirely white or invalid
	    if (safeWidth <= 0 || safeHeight <= 0) {
	        return image; // Return the original image or handle this as an error case
	    }

	    // Crop and return the image from the calculated boundaries
	    return image.getSubimage(minX, minY, safeWidth, safeHeight);
	}
	
public List<Color> findDominantColors(String imagePath, int k) {
	    
   	List<Color> dominantColors = new ArrayList<>(); // Initialize an empty list to store dominant colors	
  // Read the image from the given path
   Mat src = Imgcodecs.imread(imagePath);
   if (src.empty()) {
       System.out.println("Cannot read image: " + imagePath); // Check if the image is loaded properly
       return dominantColors; // Return empty list if image cannot be read
   }

   // Convert the color format from BGR to RGB
   Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2RGB);

   // Reshape the image to a 2D matrix where each row is a pixel and each column is a color channel
   Mat data = src.reshape(1, (int) src.total());

   // Convert the matrix to floating point for more precise calculations, required by k-means
   data.convertTo(data, CvType.CV_32F);

   // Implement k-means clustering to segment image into k clusters
   Mat centers = new Mat(); // This will store the center of each cluster
   Mat labels = new Mat(); // This will store the label for each data point (pixel)
   // Define criteria for k-means (maximum iterations and epsilon)
   TermCriteria criteria = new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 100, 0.1);
   Core.kmeans(data, k, labels, criteria, 1, Core.KMEANS_PP_CENTERS, centers); // Run k-means clustering

   // Convert cluster centers to an 8-bit format
   centers.convertTo(centers, CvType.CV_8UC1);
   centers = centers.reshape(3); // Reshape centers back to a 3-column matrix for RGB
   // Extract colors from the cluster centers and add to the list
   for (int i = 0; i < centers.rows(); i++) {
       double[] color = centers.get(i, 0); // Get color data for each cluster center
       Color dominantColor = new Color((int) color[0], (int) color[1], (int) color[2]); // Create color object
       dominantColors.add(dominantColor); // Add to list of dominant colors
   }
   return dominantColors; // Return the list of dominant colors
}
	
	
	
	
}
