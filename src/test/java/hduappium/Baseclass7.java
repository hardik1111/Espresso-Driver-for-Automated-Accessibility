package hduappium;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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

public class Baseclass7 {

	public AppiumDriver driver;
	public AppiumDriverLocalService appiumService;
	
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
		
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe")) // Path to Node.js executable on Windows
                .withAppiumJS(new File("C:\\Users\\hdu\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")) // Path to Appium's main.js file on Windows
                .withLogFile(new File("appium.log")) // Optional: Set log file path
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE) 
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                .withArgument(() -> "--allow-insecure", "adb_shell");// Optional: Set log level

        
        appiumService = AppiumDriverLocalService.buildService(builder);
        appiumService.start();
			
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Pixel 6 API 33"); // Replace with your device name or emulator
        caps.setCapability("automationName", "Espresso");
        caps.setCapability("app", "C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/resources/ApiDemos-debug.apk");
        caps.setCapability("appPackage", "io.appium.android.apis"); 
        
        /*capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR, "C:/Users/hdu/Downloads");
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, "C:/Users/hdu/Downloads/chromedriver.exe");
        capabilities.setCapability("autoGrantPermissions", "true");*/
        
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        resetFontSizeToDefault();
        
	}
	
	public void longPressAction(WebElement ele)
	{
		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId",((RemoteWebElement)ele).getId(),
						"duration",2000));
	}
	
	 
	
	
	public void scrollToEndAction()
	{
		boolean canScrollMore;
		do
		{
		 canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
			    "left", 100, "top", 100, "width", 200, "height", 200,
			    "direction", "down",
			    "percent", 3.0
			    
			));
		}while(canScrollMore);
	}
	
	
	public void swipeAction(WebElement ele,String direction)
	{
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
				"elementId", ((RemoteWebElement)ele).getId(),
			 
			    "direction", direction,
			    "percent", 0.75
			));
		
		
	}
	
	
	
	private static int convertPixelsToDp(int pixels, float dpi) {
        return (int) (pixels / (dpi / 160));
    }
    
    public static Dimension getSizeInDp(WebElement element, float dpi) {
        Dimension sizeInPixels = element.getSize();
        int widthInDp = convertPixelsToDp(sizeInPixels.getWidth(), dpi);
        int heightInDp = convertPixelsToDp(sizeInPixels.getHeight(), dpi);
        return new Dimension(widthInDp, heightInDp);
    }
	
    public List<AccessibilityViolation> checkContentDescription(List<WebElement> elements) {
        List<AccessibilityViolation> violations = new ArrayList<>();
        for (WebElement element : elements) {
            String contentDesc = element.getAttribute("content-desc");
            if (contentDesc == null || contentDesc.isEmpty()) {
                violations.add(new AccessibilityViolation(element.toString(), "Missing Content Description", element.getText()));
            }
        }
        return violations;
    }

    public List<AccessibilityViolation> checkTouchTargetSize(List<WebElement> elements, int minWidth, int minHeight) {
        List<AccessibilityViolation> violations = new ArrayList<>();
        for (WebElement element : elements) {
            Dimension size = getSizeInDp(element,420);
            if (size.width < minWidth || size.height < minHeight) {
                String details = "Size: " + size.width + "x" + size.height + "px";
                violations.add(new AccessibilityViolation(element.toString(), "Inadequate Touch Target Size", details));
            }
        }
        return violations;
    }
    
    public List<AccessibilityViolation> checkFocusability(List<WebElement> elements) {
        List<AccessibilityViolation> violations = new ArrayList<>();

        for (WebElement element : elements) {
            // Check if the element is focusable
            boolean isFocusable = Boolean.parseBoolean(element.getAttribute("focusable"));
            
            if (!isFocusable) {
                // If the element is not focusable, add it to the violations list
                violations.add(new AccessibilityViolation(element.toString(), "Element Not Focusable", element.getAttribute("class")));
            }
        }

        return violations;
    }
    
    public List<WebElement> combineElements(List<List<WebElement>> listOfLists)
    {
    	
    	List<WebElement> combinedList = new ArrayList<>();
    	
    	for (List<WebElement> individualList : listOfLists) {
 		    if (!individualList.isEmpty()) {
 		    	combinedList.addAll(individualList);
 		    }
         }
		return combinedList;
    	
    }
    
    public List<AccessibilityViolation> checkTextVisibilityAndReadability(List<WebElement> elements) {
        List<AccessibilityViolation> violations = new ArrayList<>();

        for (WebElement element : elements) {
            // Check if the element is displayed
            if (!element.isDisplayed()) {
                violations.add(new AccessibilityViolation(element.toString(), "Text Not Visible", element.getText()));
                continue;
            }

            // Check if the text is not empty
            String text = element.getText();
            if (text == null || text.trim().isEmpty()) {
                violations.add(new AccessibilityViolation(element.toString(), "Text Not Readable", "The text is empty or whitespace."));
            }
        }
        return violations;
    }
    
    public List<AccessibilityViolation> checkInteractiveElementStates(List<WebElement> elements) {
        List<AccessibilityViolation> violations = new ArrayList<>();

        for (WebElement element : elements) {
            boolean isEnabled = element.isEnabled();
            boolean isDisplayed = element.isDisplayed();
            
            // Example check: Element should be enabled but is not
            if (isDisplayed && !isEnabled) {
                violations.add(new AccessibilityViolation(
                    element.toString(),
                    "Element Disabled",
                    "This interactive element is displayed but not enabled."));
            }
            
            // Add more state checks as needed, e.g., element.isSelected() for toggle states
            
            // Focused state check (if applicable)
            // Note: Checking focus might not always be straightforward in all testing environments or might require specific setup to verify.
        }
        return violations;
    }
    
    public List<AccessibilityViolation> checkTextScalingAdaptation(List<WebElement> textElements) {
        List<AccessibilityViolation> violations = new ArrayList<>();
         

        for (WebElement textElement : textElements) {
            if (!textElement.isDisplayed()) {
                violations.add(new AccessibilityViolation(textElement.getText(), "Text Not Visible", "Text element is not visible at current text scaling."));
                continue;
            }

            Dimension size = textElement.getSize();
            if (size.height == 0 || size.width == 0) {
                violations.add(new AccessibilityViolation(textElement.getText(), "Invalid Text Dimensions", "Text element has invalid dimensions at current text scaling."));
                continue;
            }

            // Additional checks for adaptation can be implemented based on app-specific requirements
            // For example, checking if the text size is within expected bounds (requires app-specific logic)

            // Note: Real overlap detection or checking if the text is cut off is complex and may require manual testing
        }

        return violations;
    }
    public List<AccessibilityViolation> checkColorContrast(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation> violations = new ArrayList<>();
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        int i=0;
        for (WebElement element : elements) {
           
        	Point point = element.getLocation();
        	int elementWidth = element.getSize().getWidth();
        	int elementHeight = element.getSize().getHeight();
        	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        	BufferedImage croppedImage = getCroppedImage(elementScreenshot);
        	File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot"+i+".png");
        	try {
        	    ImageIO.write(croppedImage, "png", outputFile);
        	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
        	} catch (IOException e) {
        	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
        	}
        	Map<Integer, Integer> colorMap = new HashMap<>();
        	for (int x = 0; x < croppedImage.getWidth(); x++) {
        	    for (int y = 0; y < croppedImage.getHeight(); y++) {
        	        int rgb = croppedImage.getRGB(x, y);
        	        colorMap.put(rgb, colorMap.getOrDefault(rgb, 0) + 1);
        	    }
        	}

        	List<Entry<Integer, Integer>> sortedColors = new ArrayList<>(colorMap.entrySet());
        	sortedColors.sort(Entry.comparingByValue(Comparator.reverseOrder()));

        	int mostCommonColor = sortedColors.get(0).getKey();
        	int secondMostCommonColor = sortedColors.get(1).getKey();

        	/*int centerX = elementWidth / 2;
        	int centerY = elementHeight / 2;
        	int foregroundRGB = croppedImage.getRGB(centerX, centerY);*/
        	Color foregroundColor = new Color(secondMostCommonColor);
        	
        	
        	/*int edgeX = 10; // or elementWidth - 1 for the right edge
        	int edgeY = 10; // or for the bottom edge
        	int backgroundRGB = croppedImage.getRGB(edgeX, edgeY);*/
        	Color backgroundColor = new Color(mostCommonColor);
        	//int borderWidth = (int) (elementWidth * 0.36);//Math.max(1, Math.min(elementWidth, elementHeight) / 20); // 5% of the smaller dimension
        	
        	
        	//Color backgroundColor = getBorderColor(elementScreenshot,borderWidth);
        	double contrastRatio = calculateContrastRatio(foregroundColor, backgroundColor);
        	boolean contrastIsSufficient;
        	if(contrastRatio < 4.5)
        	{
        		contrastIsSufficient = false;
        	}
        	else
        	{
        		contrastIsSufficient = true;
        	}
        	
            if (!contrastIsSufficient) {
                violations.add(new AccessibilityViolation(
                    element.getText(),
                    "Insufficient Color Contrast",
                    "Element has insufficient color contrast between text and background."
                ));
            }
            
            i++;
        }

        return violations;
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
    
public void generateAccessibilityReport(List<AccessibilityViolation> violations)  {
    	
    	String fileName = "C:\\Users\\hdu\\eclipse\\Espresso_Integration\\src\\test\\java\\Testresult\\accessibility_violations_report.csv";
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            StringBuilder sb = new StringBuilder();

            // Writing the header
            sb.append("Element");
            sb.append(',');
            sb.append("Violation Type");
            sb.append(',');
            sb.append("Details");
            sb.append('\n');
        // Reporting logic
            for (AccessibilityViolation violation : violations) {
                sb.append(violation.getElementDescription());
                sb.append(',');
                sb.append(violation.getViolationType());
                sb.append(',');
                sb.append(violation.getAdditionalDetails().replace(",", ";")); // Replace commas to keep CSV format
                sb.append('\n');
            }
            
            writer.write(sb.toString());
            System.out.println("Accessibility report generated: " + fileName);
    }
        
        catch (FileNotFoundException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
        
}
	
	public Double getFormattedAmount(String amount)
	{
		Double price = Double.parseDouble(amount.substring(1));
		return price;
		
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
		appiumService.stop();
	}
	
	private double linearize(double value) {
	    return (value <= 0.03928) ? value / 12.92 : Math.pow((value + 0.055) / 1.055, 2.4);
	}

	private double getLuminance(Color color) {
	    double r = linearize(color.getRed() / 255.0);
	    double g = linearize(color.getGreen() / 255.0);
	    double b = linearize(color.getBlue() / 255.0);
	    return 0.2126 * r + 0.7152 * g + 0.0722 * b;
	}

	private double calculateContrastRatio(Color foreground, Color background) {
	    double luminance1 = getLuminance(foreground) + 0.05;
	    double luminance2 = getLuminance(background) + 0.05;
	    return (luminance1 > luminance2) ? (luminance1 / luminance2) : (luminance2 / luminance1);
	} 
	
	private BufferedImage getCroppedImage(BufferedImage image)
	{
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;

		for (int x = 0; x < image.getWidth(); x++) {
		    for (int y = 0; y < image.getHeight(); y++) {
		        if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
		            if (x < minX) minX = x;
		            if (x > maxX) maxX = x;
		            if (y < minY) minY = y;
		            if (y > maxY) maxY = y;
		        }
		    }
		}
		
		BufferedImage croppedImage= image.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
		return croppedImage;
	}
	
	
	
	
}
