package hduappium;

import java.util.List;

import org.openqa.selenium.WebElement;

public class ElementCollections {
    private List<WebElement> allElements;
    private List<WebElement> linkElements;

    public ElementCollections(List<WebElement> allElements, List<WebElement> linkElements) {
        this.allElements = allElements;
        this.linkElements = linkElements;
    }

    // Getters
    public List<WebElement> getAllElements() {
        return allElements;
    }

    public List<WebElement> getLinkElements() {
        return linkElements;
    }
}