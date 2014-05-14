package by.bsu.mmf.devteam.database.pool;

import java.util.ResourceBundle;

public class PoolConfiguration {
    private final String PATH = "by.bsu.mmf.devteam.database.pool.pool";
    private ResourceBundle bundle = ResourceBundle.getBundle(PATH);
    private String type = bundle.getString("default");
    private String driver;
    private String url;
    private String user;
    private String password;
    private int minSize;
    private int maxSize;
    private int maxConnections;
    private int timeout;

    public PoolConfiguration() {
        init();
    }

    private void init() {
        driver = bundle.getString(type + ".driver");
        url = bundle.getString(type + ".url");
        user = bundle.getString(type + ".user");
        password = bundle.getString(type + ".password");
        minSize = Integer.parseInt(bundle.getString(type + ".min.size"));
        maxSize = Integer.parseInt(bundle.getString(type + ".max.size"));
        maxConnections = Integer.parseInt(bundle.getString(type + ".max.connections"));
        timeout = Integer.parseInt(bundle.getString(type + ".timeout"));
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public int getTimeout() {
        return timeout;
    }
}
