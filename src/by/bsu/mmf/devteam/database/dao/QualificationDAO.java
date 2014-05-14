package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QualificationDAO extends AbstractDAO {

    public static final String SQL_FIND_ALL_QUALIFICATIONS =
            "SELECT DISTINCT qualification FROM qualifications ORDER BY qualification ASC";

    public static final String SQL_GET_QUALIFICATION_ID =
            "SELECT id FROM qualifications WHERE qualification = ?";

    public List<String> getAllQualifications () throws DAOException{
        connector = new DBConnector();
        List<String> list = new ArrayList <String>();
        try {
            statement = connector.getStatement();
            resultSet = statement.executeQuery(SQL_FIND_ALL_QUALIFICATIONS);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DAOException(".", e);
        } finally {
            connector.close();
        }
        return list;
    }

    public int defineQualification(String name) throws DAOException {
        connector = new DBConnector();
        int result = 0;
        try {
            preparedStatement = connector.getPreparedStatement(SQL_GET_QUALIFICATION_ID);
            preparedStatement.setBytes(1, name.getBytes());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return result;
    }
}
