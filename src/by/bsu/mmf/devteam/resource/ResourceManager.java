package by.bsu.mmf.devteam.resource;

import java.util.ResourceBundle;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ResourceManager {
    /* Keeps path to jsp resources */
    private static final String RESOURCE_PATH = "by.bsu.mmf.devteam.resource.Resource";

    /* Keeps path to logger resources */
    private static final String LOGGER_PATH = "by.bsu.mmf.devteam.resource.logger";

    /* Keeps path to forward pages resources */
    private static final String FORWARD_PATH = "by.bsu.mmf.devteam.resource.forward";

    /* Keeps resource object */
    private static ResourceBundle resource;

    /**
     * Return value by certain key
     *
     * @param key Key
     * @return Value
     */
    public static String getProperty(String key) {
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