package hduappium;

import java.awt.Color;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class Baseclass9 {

	public AndroidDriver driver;
	public AppiumDriverLocalService appiumService;
	private LocalDateTime timestamp;
	
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
	
    public List<AccessibilityViolation2> checkContentDescription(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        String violationType="Missing Content Description";
        int i=0;
        for (WebElement element : elements) {
            String contentDesc = element.getAttribute("content-desc");
            if (contentDesc == null || contentDesc.isEmpty()) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
            	//String identifier = "ID: " + element.getAttribute("resource-id") + ", Class: " + element.getAttribute("class");
                violations.add(new AccessibilityViolation2(screenshotPath,violationType,"There should be Content Description for this element"));
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
            Dimension size = getSizeInDp(element,dpi);
            if (size.width < minWidth || size.height < minHeight) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
            	String details = "Actual Size: " + size.width + "x" + size.height + "dp, Required Minimum: " + minWidth + "x" + minHeight + "dp";
                violations.add(new AccessibilityViolation2(screenshotPath,violationType , details));
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
            String focusable = element.getAttribute("focusable");
            if (focusable == null || !focusable.equalsIgnoreCase("true")) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
               
                violations.add(new AccessibilityViolation2(screenshotPath,violationType, "This element should be focusable"));
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
        	
        	
            if (!element.isDisplayed()) {
            	i++;
            	String screenshotPath = takeElementScreenshot(element,i,violationType);
                violations.add(new AccessibilityViolation2(screenshotPath,violationType ,"Element's isDisplayed attributr should be true"));
                continue;
            }

            // Check if the text is not empty
            String text = element.getText();
            if (text == null || text.trim().isEmpty()) {
            	j++;
            	String screenshotPath = takeElementScreenshot(element,j,violationType1);
                violations.add(new AccessibilityViolation2(screenshotPath, violationType1, "The text should not empty or whitespace."));
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
    public List<AccessibilityViolation2> checkColorContrast(List<WebElement> elements) throws IOException {
        List<AccessibilityViolation2> violations = new ArrayList<>();
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        String violationType = "Insufficient Color Contrast";
        int i=0;
        for (WebElement element : elements) {
           
        	Point point = element.getLocation();
        	int elementWidth = element.getSize().getWidth();
        	int elementHeight = element.getSize().getHeight();
        	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        	BufferedImage croppedImage = getCroppedImage(elementScreenshot);
        	
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
    
    public void generatePdfReport(List<AccessibilityViolation2> violations) {
        PdfWriter writer = null;
        String fileName = "C:\\Users\\hdu\\eclipse\\Espresso_Integration\\src\\test\\java\\Testresult\\accessibility_violations_report.pdf";
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
