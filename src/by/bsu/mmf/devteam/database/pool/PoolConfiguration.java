package by.bsu.mmf.devteam.database.pool;

import java.util.ResourceBundle;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class PoolConfiguration {
    /* Configuration file path */
    private final String PATH = "by.bsu.mmf.devteam.database.pool.pool";

    /* Initializes default settings */
    private ResourceBundle bundle = ResourceBundle.getBundle(PATH);
    private String type = bundle.getString("default");

    /* Configuration options */
    private String driver;
    private String url;
    private String user;
    private String password;
    private int minSize;
    private int maxSize;
    private int maxConnections;

    /**
     * Constructor
     */
    public PoolConfiguration() {
        init();
    }

    /**
     * This method initializes fields by default options
     */
    private void init() {
        driver = bundle.getString(type + ".driver");
        url = bundle.getString(type + ".url");
        user = bundle.getString(type + ".user");
        password = bundle.getString(type + ".password");
        minSize = Integer.parseInt(bundle.getString(type + ".min.size"));
        maxSize = Integer.parseInt(bundle.getString(type + ".max.size"));
        maxConnections = Integer.parseInt(bundle.getString(type + ".max.connections"));
    }

    /**
     * Getter for driver option
     *
     * @return Driver class
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Getter for database url
     *
     * @return Database url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter for user name
     *
     * @return Database user name
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for user password
     *
     * @return Database user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for minimum pool size
     *
     * @return Minimum pool size
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Getter for maximum pool size
     *
     * @return Maximum pool size
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Getter for number of maximum alive connections
     *
     * @return Number of maximum alive connections
     */
    public int getMaxConnections() {
        return maxConnections;
    }

}
