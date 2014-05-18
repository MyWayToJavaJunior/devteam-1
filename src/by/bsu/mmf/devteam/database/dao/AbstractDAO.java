package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Abstract database data access object
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class AbstractDAO {
    /* Can be used by heir */
    protected ResultSet resultSet;

    /* Use for send queries */
    protected DBConnector connector;

    /* Can be used by heir */
    protected PreparedStatement preparedStatement;

    /* Can be used by heir */
    protected Statement statement;

}
