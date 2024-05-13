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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

public class Baseclass14 {

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
		
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe")) // Path to Node.js executable on Windows
                .withAppiumJS(new File("C:\\Users\\hdu\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")) // Path to Appium's main.js file on Windows
                .withLogFile(new File("appium.log")) // Optional: Set log file path
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE) 
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug");// Optional: Set log level

        
        appiumService = AppiumDriverLocalService.buildService(builder);
        appiumService.start();
			
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Pixel 6 API 33"); // Replace with your device name or emulator
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", "C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/resources/standard-366-standard4-prod.apk");
        caps.setCapability("appPackage", "de.hafas.android.standard4");
        caps.setCapability("appActivity", "de.hafas.main.HafasApp");
        caps.setCapability("enforceXPath1", true);
        
        /*capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE_DIR, "C:/Users/hdu/Downloads");
        capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, "C:/Users/hdu/Downloads/chromedriver.exe");
        capabilities.setCapability("autoGrantPermissions", "true");*/
        
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //resetFontSizeToDefault();
        
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
	
	
	
	private static int convertPixelsToDp(int pixels, int dpi) {
        return (int) (pixels / (dpi / 160));
    }
    
    public static Dimension getSizeInDp(WebElement element, int dpi) {
        Dimension sizeInPixels = element.getSize();
        int widthInDp = convertPixelsToDp(sizeInPixels.getWidth(), dpi);
        int heightInDp = convertPixelsToDp(sizeInPixels.getHeight(), dpi);
        return new Dimension(widthInDp, heightInDp);
    }
	
    public List<WebElement> findChildElementsForClasses(WebElement parentElement) {
        // Initialize a list to hold all matching child elements
        List<WebElement> childElements = new ArrayList<>();

        // Define the class names you're interested in
        String[] classNames = {
            "android.widget.TextView",
            "android.widget.CheckBox",
            "android.widget.EditText"
        };

        // Loop through the class names and find elements for each class
        for (String className : classNames) {
            // Add all found elements of the current class to the list
            childElements.addAll(parentElement.findElements(By.className(className)));
        }

        return childElements;
    }
    
    public List<AccessibilityViolation2> checkContentDescription(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType="Missing Content Description";
        int i=0;
        for (WebElement element : elements) {
        	String className = element.getAttribute("class");
            boolean isClickable = Boolean.parseBoolean(element.getAttribute("clickable"));
            boolean isCheckable = Boolean.parseBoolean(element.getAttribute("checkable"));
            String contentDesc = element.getAttribute("content-desc");
            String text = element.getAttribute("text");
            boolean isSelected = Boolean.parseBoolean(element.getAttribute("selected"));
            boolean hasContentDescription = (contentDesc != null && !contentDesc.isEmpty());
            boolean hasText = (text != null && !text.isEmpty());

            // Check for clickable elements but exclude specific layouts
            if ((isClickable || isCheckable)) 
            	{
            	if(!className.equals("android.widget.RelativeLayout") && !className.equals("android.widget.FrameLayout") && !className.equals("android.widget.LinearLayout")) { 
            		if(!hasContentDescription && !hasText) {
            			i++;
            			String screenshotPath = takeElementScreenshot(element, i, violationType);
            			violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Clickable element should have Content Description or Text"));
            		}
            	}
            }

            // Check for specific layouts and TextView
            if (isClickable) {
            	if (className.equals("android.widget.RelativeLayout") || className.equals("android.widget.FrameLayout") || className.equals("android.widget.LinearLayout")) {
                if (!hasContentDescription) {
                    // If it's a layout without content-description, check for TextView child with text
                    List<WebElement> childTextViews = findChildElementsForClasses(element);
                    if (childTextViews.isEmpty())
                    {
                    	i++;
                        String screenshotPath = takeElementScreenshot(element, i, violationType);
                        violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Clickable element should have Content Description or Text"));
                    }
                    else
                    {
                    boolean childHasText = childTextViews.stream().anyMatch(e -> e.getAttribute("text") != null && !e.getAttribute("text").isEmpty());

                    if (!childHasText) {
                        i++;
                        String screenshotPath = takeElementScreenshot(element, i, violationType);
                        violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Layout element should have Content Description or a TextView child with Text"));
                    	}
                      }
                	}
            	}
            } 
            
            if (!isClickable) {
            		if (className.equals("android.widget.LinearLayout")) {
           				if(isSelected) {
           					if (!hasContentDescription) {
                    // If it's a layout without content-description, check for TextView child with text
           						List<WebElement> childTextViews = findChildElementsForClasses(element);
                    
           					if (childTextViews.isEmpty())
           					{
           							i++;
           							String screenshotPath = takeElementScreenshot(element, i, violationType);
           							violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Non Clickable but selected element should have Content Description or Text"));
           					}
           					else 
           					{
           							boolean childHasText = childTextViews.stream().anyMatch(e -> e.getAttribute("text") != null && !e.getAttribute("text").isEmpty());
           							if (!childHasText) {
           									i++;
           									String screenshotPath = takeElementScreenshot(element, i, violationType);
           									violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Layout element should have Content Description or a TextView child with Text"));
           									}
           					}
           				}
           			}
                }
            } 
            
            if (!isClickable) {
                if(className.equals("android.widget.TextView")){ // Non-clickable TextViews should have text
                if (!hasText) {
                    i++;
                    String screenshotPath = takeElementScreenshot(element, i, violationType);
                    violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Non-clickable TextView should have Text"));
                	}
                }
            }
        }
        return violations;
    }

    public List<AccessibilityViolation2> checkTouchTargetSize(List<WebElement> elements, int minWidth, int minHeight) throws NumberFormatException, IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Inadequate Touch Target Size";
        int dpi =  getDeviceDpi();
        int i=0;
        for (WebElement element : elements) {
        	 boolean isClickable = Boolean.parseBoolean(element.getAttribute("clickable"));
        	
        	if(isClickable) {
               
        	
            Dimension size = getSizeInDp(element,dpi);
            if ((size.width < minWidth) || (size.height < minHeight)) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
            	String details = "Actual Size: " + size.width + "x" + size.height + "dp, Required Minimum: " + minWidth + "x" + minHeight + "dp";
                violations.add(new AccessibilityViolation2(screenshotPath,violationType , details));
            }
        	}
        }
        return violations;
    }
    
    public List<AccessibilityViolation2> checkFocusability(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Element Not Focusable";
        int i=0;
        for (WebElement element : elements) {
            // Check if the element is focusable
        if (element.getAttribute("clickable").equalsIgnoreCase("true")) {
        	String focusable = element.getAttribute("focusable");
            if (focusable == null || !focusable.equalsIgnoreCase("true")) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
               
                violations.add(new AccessibilityViolation2(screenshotPath,violationType, "This element should be focusable"));
            }
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
    
    public List<AccessibilityViolation2> checkTextVisibilityAndReadability(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Visibility issue found!";
        String violationType1 = "Readability issue found!";
        int i=0;
        int j=0;
        for (WebElement element : elements) {
            // Check if the element is displayed
        	String elementType = element.getAttribute("class");
        	
            if (!element.isDisplayed()) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
                violations.add(new AccessibilityViolation2(screenshotPath,violationType ,"Element's isDisplayed attributr should be true"));
                continue;
            }
            if (elementType.matches("android.widget.TextView|android.widget.Button|android.widget.EditText")) {
            // Check if the text is not empty
            String text = element.getText();
            if (text == null || text.trim().isEmpty()) {
            	j++;
            	String screenshotPath = takeElementScreenshot(element,j,violationType1);
                violations.add(new AccessibilityViolation2(screenshotPath, violationType1, "The text should not empty or whitespace."));
            }
            }
        }
        return violations;
    }
    
    public List<AccessibilityViolation2> checkInteractiveElementStates(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Element Disabled";
        int i=0;
        for (WebElement element : elements) {
            boolean isEnabled = element.isEnabled();
            boolean isDisplayed = element.isDisplayed();
            
            // Example check: Element should be enabled but is not
            if (isDisplayed && !isEnabled) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
                violations.add(new AccessibilityViolation2(
                		screenshotPath,
                	violationType,
                    "This interactive element should be displayed and enabled."));
            }
            
            // Add more state checks as needed, e.g., element.isSelected() for toggle states
            
            // Focused state check (if applicable)
            // Note: Checking focus might not always be straightforward in all testing environments or might require specific setup to verify.
        }
        return violations;
    }
    
    public List<AccessibilityViolation2> checkTextScalingAdaptation(List<WebElement> textElements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType = "Text Not Visible after Textscaling";
        String violationType1 = "Invalid Text Dimensions after Textscaling";
        int i=0;
        int j=0;
        for (WebElement textElement : textElements) {
            if (!textElement.isDisplayed()) {
            	i++;
            	String screenshotPath = takeElementScreenshot(textElement,i,violationType);
                violations.add(new AccessibilityViolation2(screenshotPath, violationType, "Text element should be visible at current text scaling."));
                continue;
            }

            Dimension size = textElement.getSize();
            if (size.height == 0 || size.width == 0) {
            	j++;
            	String screenshotPath = takeElementScreenshot(textElement,j,violationType1);
                violations.add(new AccessibilityViolation2(screenshotPath, violationType1, "Text element should have valid dimensions at current text scaling."));
                continue;
            }

            // Additional checks for adaptation can be implemented based on app-specific requirements
            // For example, checking if the text size is within expected bounds (requires app-specific logic)

            // Note: Real overlap detection or checking if the text is cut off is complex and may require manual testing
        }

        return violations;
    }
    public List<AccessibilityViolation2> checkColorContrastText(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        String violationType = "Insufficient Color Contrast Text";
        int i=0;
        for (WebElement element : elements) {
           
        	Point point = element.getLocation();
        	int elementWidth = element.getSize().getWidth();
        	int elementHeight = element.getSize().getHeight();
        	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        	File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png");
        	try {
        	    ImageIO.write(elementScreenshot, "png", outputFile);
        	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
        	} catch (IOException e) {
        	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
        	}
        	
        	List<Color> colors= findDominantColors("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png", 2);
        	if (colors.size() >= 2) {
        	    Color mostDominantColor = colors.get(0);
        	    Color secondMostDominantColor = colors.get(1);

        	    double contrastRatio = calculateContrastRatio(mostDominantColor, secondMostDominantColor);
        	    if (contrastRatio < 4.5) {
            		i++;
            		String screenshotPath = takeElementScreenshot(element,i,violationType);
                    // Assuming AccessibilityViolation constructor and element identification logic is correctly implemented
                    violations.add(new AccessibilityViolation2(
                    	screenshotPath,
                        violationType,
                        "Contrast ratio:"+contrastRatio+" should be at least 4.5"
                    ));
                }
        	}
        	
        	//double contrastRatio = calculateContrastRatio(foregroundColor, backgroundColor);
        	
            
            
        }

        return violations;
    }
    
    public List<AccessibilityViolation2> checkColorContrastNonText(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        String violationType = "Insufficient Color Contrast Non Text";
        int i=0;
        for (WebElement element : elements) {
           
        	Point point = element.getLocation();
        	int elementWidth = element.getSize().getWidth();
        	int elementHeight = element.getSize().getHeight();
        	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        	File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png");
        	try {
        	    ImageIO.write(elementScreenshot, "png", outputFile);
        	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
        	} catch (IOException e) {
        	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
        	}
        	
        	List<Color> colors= findDominantColors("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png", 2);
        	if (colors.size() >= 2) {
        	    Color mostDominantColor = colors.get(0);
        	    Color secondMostDominantColor = colors.get(1);

        	    double contrastRatio = calculateContrastRatioNonText(mostDominantColor, secondMostDominantColor);
        	    if (contrastRatio < 3) {
            		i++;
            		String screenshotPath = takeElementScreenshot(element,i,violationType);
                    // Assuming AccessibilityViolation constructor and element identification logic is correctly implemented
                    violations.add(new AccessibilityViolation2(
                    	screenshotPath,
                        violationType,
                        "Contrast ratio:"+contrastRatio+" should be at least 3"
                    ));
                }
        	}
        	
        	//double contrastRatio = calculateContrastRatio(foregroundColor, backgroundColor);
        	
            
            
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
        String fileName = "C:\\Users\\hdu\\eclipse\\Espresso_Integration\\src\\test\\java\\Testresult\\accessibility_violations_report"+ScreenName+".pdf";
        Set<String> uniqueEntries = new HashSet<>();
        
        try {
            writer = new PdfWriter(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        for (AccessibilityViolation2 violation : violations) {
            String imagePath = violation.getElementDescription();
            String uniqueIdentifier = imagePath + "_" + violation.getViolationType() + "_" + violation.getAdditionalDetails();

            if (!uniqueEntries.add(uniqueIdentifier)) continue;
            File imageFile = new File(imagePath);

            // Check if image exists
            if (!imageFile.exists()) continue;

            // Adding screenshot
            try {
                ImageData data = ImageDataFactory.create(imagePath);
                Image img = new Image(data);
                /*Rectangle pageSize = pdf.getDefaultPageSize();
                float availableWidth = pageSize.getWidth() - document.getLeftMargin() - document.getRightMargin();
                float availableHeight = pageSize.getHeight() - document.getTopMargin() - document.getBottomMargin();

                // Calculate scale to fit the image within available dimensions while maintaining the aspect ratio
                float widthScale = availableWidth / img.getImageWidth();
                float heightScale = availableHeight / img.getImageHeight();
                float scale = Math.min(widthScale, heightScale); // Choose the smallest scale to fit the image

                // Scale the image
                img.scale(scale, scale);

                // Optionally, you can center the image
                float offsetX = (availableWidth - (img.getImageWidth() * scale)) / 2;
                float offsetY = (availableHeight - (img.getImageHeight() * scale)) / 2;
                img.setFixedPosition(document.getPageNumber(), offsetX + document.getLeftMargin(), offsetY + document.getBottomMargin()); */

                document.add(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            // Adding Violation Type and Details
            document.add(new Paragraph("Violation Type: " + violation.getViolationType()));
            document.add(new Paragraph("Details: " + violation.getAdditionalDetails()));

            // Add a separator for readability
            document.add(new Paragraph("\n---------------------------\n"));
        }

        document.close();
        System.out.println("PDF report generated: " + fileName);
    }
	
	public Double getFormattedAmount(String amount)
	{
		Double price = Double.parseDouble(amount.substring(1));
		return price;
		
	}
	
	public String takeElementScreenshot(WebElement element,int count,String violationType) throws IOException {
		
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		
		
	    BufferedImage fullImg = ImageIO.read(screen);

	    // Get the location of element on the page
	    Point point = element.getLocation();

	    // Get width and height of the element
	    int elementWidth = element.getSize().getWidth();
	    int elementHeight = element.getSize().getHeight();

	    // Crop the entire page screenshot to get only element screenshot
	    BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
	    BufferedImage croppedImage = getCroppedImage(elementScreenshot);
	    String time = getFormattedTimestamp();
		File fileName = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Violation-SS/"+violationType+"_"+time+"_"+count+".png");
		
		try {
    	    ImageIO.write(croppedImage, "png", fileName);
    	    System.out.println("Saved element screenshot to " + fileName.getAbsolutePath());
    	} catch (IOException e) {
    	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
    	}
		
		return fileName.getAbsolutePath();
	    // Implement screenshot capturing and saving logic here
	    // Return the path to the saved screenshot file
	}
	
	public String getFormattedTimestamp() {
		this.timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        return ((LocalDateTime) this.timestamp).format(formatter);
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

	private double calculateContrastRatio(Color background, Color foreground) {
	    double luminance1 = getLuminance(foreground) + 0.05;
	    double luminance2 = getLuminance(background) + 0.05;
	    return (luminance1 > luminance2) ? (luminance1 / luminance2) : (luminance2 / luminance1);
	} 
	
	private double calculateContrastRatioNonText(Color background, Color foreground) {
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

	    // Check every pixel to find the bounding box of non-white content
	    for (int x = 0; x < image.getWidth(); x++) {
	        for (int y = 0; y < image.getHeight(); y++) {
	            if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
	                minX = Math.min(minX, x);
	                maxX = Math.max(maxX, x);
	                minY = Math.min(minY, y);
	                maxY = Math.max(maxY, y);
	            }
	        }
	    }

	    // Ensure the coordinates do not exceed the image bounds and adjust if necessary
	    minX = Math.max(0, minX);
	    minY = Math.max(0, minY);
	    // Ensuring maxX and maxY do not exceed image dimensions minus 1 (0-indexed)
	    maxX = Math.min(image.getWidth() - 1, maxX);
	    maxY = Math.min(image.getHeight() - 1, maxY);

	    // Calculate safe width and height to avoid RasterFormatException
	    int safeWidth = maxX - minX + 1;
	    int safeHeight = maxY - minY + 1;

	    // Prevent negative or zero dimensions for subimage which can occur if the image is completely white
	    if (safeWidth <= 0 || safeHeight <= 0) {
	        return image; // or return null; depending on how you want to handle this case
	    }

	    // Create and return the cropped image
	    return image.getSubimage(minX, minY, safeWidth, safeHeight);
	}
	public List<Color> findDominantColors(String imagePath, int k) {
	    
   	 List<Color> dominantColors = new ArrayList<>();	
   // Read the image
   Mat src = Imgcodecs.imread(imagePath);
   if (src.empty()) {
       System.out.println("Cannot read image: " + imagePath);
       return dominantColors;
   }

   // Convert to RGB
   Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2RGB);

   // Reshape the image to a 2D matrix
   Mat data = src.reshape(1, (int) src.total());

   // Convert to floating point
   data.convertTo(data, CvType.CV_32F);

   // Implement k-means clustering
   Mat centers = new Mat();
   Mat labels = new Mat();
   TermCriteria criteria = new TermCriteria(TermCriteria.MAX_ITER + TermCriteria.EPS, 100, 0.1);
   Core.kmeans(data, k, labels, criteria, 1, Core.KMEANS_PP_CENTERS, centers);

   // Convert centers to proper format and extract dominant colors
   centers.convertTo(centers, CvType.CV_8UC1);
   centers = centers.reshape(3);

   for (int i = 0; i < centers.rows(); i++) {
       double[] color = centers.get(i, 0);
       Color dominantColor = new Color((int) color[0], (int) color[1], (int) color[2]);
       dominantColors.add(dominantColor);
   }
   return dominantColors;
}
	
	
	
	
}