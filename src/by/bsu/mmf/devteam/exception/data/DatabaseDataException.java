package by.bsu.mmf.devteam.exception.data;

/**
 * This class defines exception which throws on dao layer
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class DatabaseDataException extends DAOException {

    /**
     * Constructor
     *
     * @param s Message
     */
    public DatabaseDataException(String s) {
        super(s);
    }

}

