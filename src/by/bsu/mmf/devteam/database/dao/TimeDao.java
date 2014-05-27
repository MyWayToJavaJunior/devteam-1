package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class TimeDao extends AbstractDAO {
    /** Initializes logger */
    private static Logger logger = Logger.getLogger("db");

    /** Logger messages */
    private static final String ERROR_SAVE_ELAPSED = "logger.db.error.save.elapsed";
    private static final String INFO_SAVE_ELAPSED = "logger.db.info.save.elapsed";
    private static final String ERROR_UPDATE_ELAPSED = "logger.db.error.update.elapsed";
    private static final String INFO_UPDATE_ELAPSED = "logger.db.info.update.elapsed";
    private static final String ERROR_GET_ELAPSED = "logger.db.error.get.elapsed";
    private static final String INFO_GET_ELAPSED = "logger.db.info.get.elapsed";
    private static final String ERROR_IS_EXIST_ELAPSED = "logger.db.error.is.exist.elapsed";
    private static final String ERROR_GET_TOTAL_ELAPSED = "logger.db.error.get.total.elapsed";
    private static final String INFO_GET_TOTAL_ELAPSED = "logger.db.info.get.total.elapsed";
    private static final String ERROR_GET_ELAPSED_BY_MAIL = "logger.db.error.get.elapsed.by.mail";
    private static final String INFO_GET_ELAPSED_BY_MAIL = "logger.db.info.get.elapsed.by.mail";

    /**
     * This query sets time which employee elapsed on job. <br />
     * Requires to set job id, user id and time.
     */
    private static final String SQL_SET_ELAPSED_TIME =
            "INSERT INTO elapsed (jid, uid, time) VALUES (?, ?, ?)";

    /**
     * This query updates existing elapsed time. <br />
     * Requires to set time, user id and job id.
     */
    private static final String SQL_UPDATE_EXISTING_ELAPSED_TIME =
            "UPDATE elapsed SET time = ? WHERE uid = ? AND jid = ?";

    /**
     * This query searches existing elapsed time. <br />
     * Requires to set job id and user id.
     */
    private static final String SQL_FIND_EXISTING_ELAPSED_TIME =
            "SELECT * FROM elapsed WHERE jid = ? AND uid = ?";

    /**
     * This query searches existing time or not.
     */
    private static final String SQL_IS_EXIST_ELAPSED_TIME =
            "SELECT * FROM elapsed WHERE jid = ? AND uid = ?";

    /**
     * This query calculate total elapsed time on project. <br />
     * Requires to set specification id.
     */
    private static final String SQL_FIND_ELAPSED_TIME_ON_PROJECT_BY_SPECIFICATION =
            "SELECT SUM(time) FROM elapsed WHERE jid IN (SELECT id FROM jobs WHERE sid = ?)";

    /**
     * This query searching how much elapsed certain employee. <br />
     * Requires to set mail of employee.
     */
    private static final String SQL_FIND_EMPLOYEE_ELAPSED_TIME_ON_PROJECT_BY_MAIL =
            "SELECT time FROM elapsed WHERE uid = (SELECT id FROM users WHERE mail = ?)";

    /**
     * Saves elapsed time on certain job.
     *
     * @param uid User id
     * @param jid Job id
     * @param time Time
     * @throws DAOException
     */
    public void saveElapsedTime(int uid, int jid, int time) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_SET_ELAPSED_TIME);
            preparedStatement.setInt(1, jid);
            preparedStatement.setInt(2, uid);
            preparedStatement.setInt(3, time);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_SAVE_ELAPSED) + uid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_SAVE_ELAPSED) + uid);
    }

    /**
     * Updates elapsed time if employee sets not the first time.
     *
     * @param uid User id
     * @param jid Job id
     * @param time Time
     * @throws DAOException
     */
    public void updateElapsedTime(int uid, int jid, int time) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_UPDATE_EXISTING_ELAPSED_TIME);
            preparedStatement.setInt(1, time);
            preparedStatement.setInt(2, uid);
            preparedStatement.setInt(3, jid);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_UPDATE_ELAPSED) + uid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_UPDATE_ELAPSED) + uid);
    }

    /**
     * Returns elapsed time on certain job which employee sets at last time.
     *
     * @param uid Employee id
     * @param jid Job id
     * @return Time which sets employee
     * @throws DAOException object if execution of query is failed
     */
    public int getExistingElapsedTime(int uid, int jid) throws DAOException {
        int time = 0;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_EXISTING_ELAPSED_TIME);
            preparedStatement.setInt(1, jid);
            preparedStatement.setInt(2, uid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                time = resultSet.getInt("time");
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_ELAPSED) + uid + "," + jid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_ELAPSED) + uid + "," + jid);
        return time;
    }

    /**
     * Checks existing of time sets by employee.
     *
     * @param uid Employee id
     * @param jid Job id
     * @return True if elapsed time is already exist
     * @throws DAOException object if execution of query is failed
     */
    public boolean isExistElapsedTime(int uid, int jid) throws DAOException {
        boolean exist = false;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_IS_EXIST_ELAPSED_TIME);
            preparedStatement.setInt(1, jid);
            preparedStatement.setInt(2, uid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_IS_EXIST_ELAPSED) + uid + "," + jid, e);
        } finally {
            connector.close();
        }
        return exist;
    }

    /**
     * Returns total time which elapsed on whole project by all employees.
     *
     * @param sid Specification id
     * @return Total elapsed time
     * @throws DAOException object if execution of query is failed
     */
    public int getTotalElapsedTimeOnProject(int sid) throws DAOException {
        int time = 0;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_ELAPSED_TIME_ON_PROJECT_BY_SPECIFICATION);
            preparedStatement.setInt(1, sid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getObject(1) != null) {
                    time = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_TOTAL_ELAPSED) + sid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_TOTAL_ELAPSED) + sid);
        return time;
    }

    /**
     * Returns how mush certain employee elapsed on job.
     *
     * @param mail User mail
     * @return Time elapsed on project
     * @throws DAOException object if execution of query is failed
     */
    public int getEmployeeElapsedTimeOnProject(String mail) throws DAOException {
        int time = 0;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_EMPLOYEE_ELAPSED_TIME_ON_PROJECT_BY_MAIL);
            preparedStatement.setBytes(1, mail.getBytes());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                time = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_ELAPSED_BY_MAIL) + mail, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_ELAPSED_BY_MAIL) + mail);
        return time;
    }

}
