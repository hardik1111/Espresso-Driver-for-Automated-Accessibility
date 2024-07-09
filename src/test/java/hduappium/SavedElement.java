package hduappium;

import java.util.List;
import java.util.Map;



import java.util.List;
import java.util.Map;

public class SavedElement {
    private Map<String, String> attributes;
    private List<ChildElement> childElements;
    private int width;
    private int height;
    private String screenshotPath; 

    public SavedElement(Map<String, String> attributes, List<ChildElement> childElements, int width, int height,String screenshotPath) {
        this.attributes = attributes;
        this.childElements = childElements;
        this.width = width;
        this.height = height;
        this.screenshotPath = screenshotPath;
        
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<ChildElement> getChildElements() {
        return childElements;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public String getScreenshotPath() {
        return screenshotPath;
    }
}
