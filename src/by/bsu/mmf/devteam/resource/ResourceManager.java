package by.bsu.mmf.devteam.resource;

import java.util.ResourceBundle;

public class ResourceManager {
    private static final String RESOURCE_PATH = "by.bsu.mmf.devteam.resource.Resource";
    private static final String LOGGER_PATH = "by.bsu.mmf.devteam.resource.logger";
    private static final String FORWARD_PATH = "by.bsu.mmf.devteam.resource.forward";
    private static ResourceBundle resource;

    public static String getProperty(String key) {
        int pos = key.indexOf(".");
        String type = key.substring(0, key.indexOf("."));
        switch (type) {
            case "jsp":
                resource  = ResourceBundle.getBundle(RESOURCE_PATH);
                break;
            case "logger":
                resource = ResourceBundle.getBundle(LOGGER_PATH);
                break;
            case "forward":
                resource = ResourceBundle.getBundle(FORWARD_PATH);
                break;
            case "redirect":
                resource = ResourceBundle.getBundle(FORWARD_PATH);
                break;
        }
        return resource.getString(key);
    }

}