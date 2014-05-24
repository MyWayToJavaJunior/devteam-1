package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements DAO pattern
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class QualificationDAO extends AbstractDAO {
    /** Initializing database activity logger */
    private static Logger logger = Logger.getLogger("db");

    /** Logger messages */
    private static final String ERROR_GET_QUALIFICATIONS = "logger.db.error.get.qualifications";
    private static final String INFO_GET_QUALIFICATIONS = "logger.db.info.get.qualifications";
    private static final String ERROR_DEFINE_QUALIFICATION = "logger.db.error.define.qualification";
    private static final String INFO_DEFINE_QUALIFICATION = "logger.db.info.define.qualification";

    /**
     * This query searches unique list of qualifications.
     */
    public static final String SQL_FIND_ALL_QUALIFICATIONS =
            "SELECT DISTINCT qualification FROM qualifications ORDER BY qualification ASC";

    /**
     * This query searches qualification id by qualification name.
     */
    public static final String SQL_GET_QUALIFICATION_ID =
            "SELECT id FROM qualifications WHERE qualification = ?";

    /**
     * Returns unique list of all qualifications
     *
     * @return List of qualifications
     * @throws DAOException object if execution of query is failed
     */
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
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_QUALIFICATIONS), e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_QUALIFICATIONS));
        return list;
    }

    /**
     * Return qualification id by name
     *
     * @param name Qualification name
     * @return Qualification id
     * @throws DAOException object if execution of query is failed
     */
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
            throw new DAOException(ResourceManager.getProperty(ERROR_DEFINE_QUALIFICATION) + name, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_DEFINE_QUALIFICATION) + name);
        return result;
    }

}
