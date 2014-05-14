package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AbstractDAO {
    protected ResultSet resultSet;
    protected DBConnector connector;
    protected PreparedStatement preparedStatement;
    protected Statement statement;
}
