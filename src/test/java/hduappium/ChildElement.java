package hduappium;

import java.util.Map;

public class ChildElement {
    private Map<String, String> attributes;

    public ChildElement(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
    
    public String getText() {
        return attributes.get("text");
    }
    
    
}