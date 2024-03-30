package hduappium;

import static org.testng.Assert.assertNotNull;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;


public class AccessibilityTest8 extends Baseclass5 {
	
	
    @Test
	public void accessibilityTest() throws InterruptedException, IOException, TesseractException
    {
    	
    	
        boolean b = changeFontSize(driver,1.5f);
        if(b==true)
        {
        	List<WebElement> textViews = driver.findElements(By.xpath("//android.widget.TextView"));
            //List<WebElement> imagebutton = driver.findElements(By.xpath("//android.widget.ImageButton"));
            //List<WebElement> elements = new ArrayList<>();
            //elements.addAll(imageViews);
            //elements.addAll(textViews);
            //elements.addAll(imagebutton);
            // Common base class for UI elements
        	
        	String elementID = ((RemoteWebElement) textViews.get(0)).getId();
        	System.out.println(elementID+" ----------------------------------------------------------------------------------------------------------------------------");
        	File screenshot = driver.getScreenshotAs(OutputType.FILE);
        	BufferedImage fullImg = ImageIO.read(screenshot);
        	//int i=0;
        	//int maxWidth = 300; // maximum width
        	//int maxHeight = 300; // maximum height
        	/* for (WebElement element : elements) {
        		if(i<elements.size())
        		{
        	    System.out.println("Element: " + element.getAttribute("text"));
        	    Point point = element.getLocation();
            	int elementWidth = element.getSize().getWidth();
            	int elementHeight = element.getSize().getHeight();
            	
            	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
            		File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot"+i+".png");
            	try {
            	    ImageIO.write(elementScreenshot, "png", outputFile);
            	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
            	} catch (IOException e) {
            	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
            	}
            	
            	i++;
        		}
        		
        		else
        		{
        			break;
        		}
        	    
        	} */
        	
        	

        	// Get the location of element on the page
        

        	// Get width and height of the element

        	/*WebElement element = textViews.get(0);
        	//Crop the entire page screenshot to get only element screenshot
        	Point point = element.getLocation();
        	int elementWidth = element.getSize().getWidth();
        	int elementHeight = element.getSize().getHeight();
        	BufferedImage elementScreenshot = fullImg.getSubimage(point.getX(), point.getY(), elementWidth, elementHeight);
        	BufferedImage croppedImage = getCroppedImage(elementScreenshot);
        	File outputFile = new File("C:/Users/hdu/eclipse/Espresso_Integration/src/test/java/Screenshots/element_screenshot.png");
        	try {
        	    ImageIO.write(croppedImage, "png", outputFile);
        	    System.out.println("Saved element screenshot to " + outputFile.getAbsolutePath());
        	} catch (IOException e) {
        	    System.err.println("Error while saving the element screenshot: " + e.getMessage());
        	} */
        	ITesseract instance = new Tesseract();
            instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata/");
            int dpi = 420; // Example screen DPI
            
            // Perform OCR to get text and its bounding box
    		java.util.List<Word> words = instance.getWords(fullImg, TessPageIteratorLevel.RIL_WORD);
    		for (Word word : words) {
    			
    			Rectangle boundingBox = word.getBoundingBox();
                int boundingBoxHeight = boundingBox.height;
                double points = (boundingBoxHeight / (double) dpi) * 72;
    		    System.out.println(word.getText() + " " + word.getBoundingBox()+" " + points);
    		    // Use the bounding box height as a proxy for font size
    		}
        }
    	//List<WebElement> imageViews = driver.findElements(By.xpath("//android.widget.ImageView"));
        
		
		
    }
    	// ...
    	/*private Color getBorderColor(BufferedImage elementScreenshot, int borderWidth) {
        long sumR = 0, sumG = 0, sumB = 0;
        int count = 0;

        // Top and Bottom border
        for (int x = 0; x < elementScreenshot.getWidth(); x++) {
            for (int y = 0; y < borderWidth; y++) { // Top border
                Color color = new Color(elementScreenshot.getRGB(x, y));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
                count++;
            }
            for (int y = elementScreenshot.getHeight() - borderWidth; y < elementScreenshot.getHeight(); y++) { // Bottom border
                Color color = new Color(elementScreenshot.getRGB(x, y));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
                count++;
            }
        }

        // Left and Right border
        for (int y = borderWidth; y < elementScreenshot.getHeight() - borderWidth; y++) {
            for (int x = 0; x < borderWidth; x++) { // Left border
                Color color = new Color(elementScreenshot.getRGB(x, y));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
                count++;
            }
            for (int x = elementScreenshot.getWidth() - borderWidth; x < elementScreenshot.getWidth(); x++) { // Right border
                Color color = new Color(elementScreenshot.getRGB(x, y));
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
                count++;
            }
        }

        return new Color((int) (sumR / count), (int) (sumG / count), (int) (sumB / count));
    }*/

    	// Helper methods to calculate contrast ratio
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
    																									


    