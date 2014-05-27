package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.exception.data.DatabaseDataException;
import by.bsu.mmf.devteam.logic.bean.user.Role;
import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.RoleDefiner;
import by.bsu.mmf.devteam.logic.bean.user.User;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements DAO pattern
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class UserDAO extends AbstractDAO {
    /** Initializing database activity logger */
    private static Logger logger = Logger.getLogger("db");

    /** Logger messages */
    private static final String ERROR_GET_PASSWORD = "logger.db.error.get.password";
    private static final String INFO_GET_PASSWORD = "logger.db.info.get.password";
    private static final String ERROR_GET_USER = "logger.db.error.get.user";
    private static final String INFO_GET_USER = "logger.db.info.get.user";
    private static final String ERROR_GET_USER_ROLE = "logger.db.error.get.user.role";
    private static final String INFO_GET_USER_ROLE = "logger.db.info.get.user.role";
    private static final String DATA_ROLE_NOT_FOUND = "logger.db.data.role.not.found";
    private static final String ERROR_GET_USER_MAILS = "logger.db.error.get.user.mails";
    private static final String INFO_GET_USER_MAILS = "logger.db.info.get.user.mails";
    private static final String ERROR_TAKE_EMPLOYEE = "logger.db.error.take.employee";
    private static final String INFO_TAKE_EMPLOYEE = "logger.db.info.take.employee";
    private static final String ERROR_IS_FREE_EMPLOYEE = "logger.db.error.is.free.employee";
    private static final String INFO_IS_FREE_EMPLOYEE = "logger.db.info.is.free.employee";
    private static final String ERROR_IS_FREE_EMPLOYEE_MAIL = "logger.db.error.is.free.employee.mail";
    private static final String INFO_IS_FREE_EMPLOYEE_MAIL = "logger.db.info.is.free.employee.mail";
    private static final String ERROR_GET_USER_MAIL = "logger.db.error.get.user.mail";
    private static final String INFO_GET_USER_MAIL = "logger.db.info.get.user.mail";
    private static final String ERROR_GET_WORKING_MAILS = "logger.db.error.get.users.working.on.job";
    private static final String INFO_GET_WORKING_MAILS = "logger.db.info.get.users.working.on.job";
    private static final String ERROR_EXEMPT_EMPLOYEES = "logger.db.error.exempt.employee";
    private static final String INFO_EXEMPT_EMPLOYEES = "logger.db.info.exempt.employee";

    /**
     * This query searches user hashed password. <br />
     * Requires to set user mail.
     */
    private static final String SQL_FIND_PASSWORD_BY_LOGIN =
            "SELECT password FROM users WHERE mail = ?";

    /**
     * This query searches role name in database. <br />
     * Requires to set user id.
     */
    private static final String SQL_FIND_USER_ROLE_BY_USER_ID =
            "SELECT role.name FROM users INNER JOIN role ON users.rid = role.id WHERE users.id = ?";

    /**
     * This query searches user data by mail and password. <br />
     * Requires to set user mail and user hashed password.
     */
    private static final String SQL_FIND_USER_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM users WHERE mail = ? AND password = ?";

    /**
     * This query searches employees by certain qualification.
     */
    private static final String SQL_FIND_EMPLOYEES_BY_QUALIFICATION =
            "SELECT * FROM users INNER JOIN qualifications ON users.qualification = qualifications.id" +
            "WHERE qualifications.qualification = ?";

    /**
     * This query searches free users mails by qualification.
     */
    private static final String SQL_FIND_USERS_MAILS_BY_QUALIFICATION_AND_FREE_STATUS =
            "SELECT users.mail FROM users INNER JOIN qualifications ON users.qualification = qualifications.id " +
            "INNER JOIN employment ON  employment.uid = users.id " +
            "AND employment.jid = 0 AND qualifications.qualification = ?";

    /**
     * This query appoints employee to certain job. <br />
     * Requires to set job id and user mail.
     */
    private static final String SQL_TAKE_EMPLOYEE =
            "UPDATE employment SET jid = ? WHERE uid = (SELECT id FROM users WHERE mail = ?)";

    /**
     * This query searches employee status. <br />
     * Requires to set user id.
     */
    private static final String SQL_CHECK_STATUS =
            "SELECT jid FROM employment WHERE uid = ?";

    /**
     * This query searches employee status. <br />
     * Requires to set user mail.
     */
    private static final String SQL_CHECK_STATUS_BY_MAIL =
            "SELECT jid FROM employment INNER JOIN users ON employment.uid = users.id WHERE users.mail = ?";

    /**
     * This query searches user mail by user id.
     */
    private static final String SQL_FIND_USER_MAIL_BY_ID =
            "SELECT mail FROM users WHERE id = ?";

    /**
     * This query searches employee mails which working on certain job.
     */
    private static final String SQL_FIND_EMPLOYEE_MAILS_BY_JOB_ID =
            "SELECT mail FROM users WHERE id IN (SELECT uid FROM employment WHERE jid = ?)";

    /**
     * This query appoint employee on certain job.
     */
    private static final String SQL_EXEMPT_EMPLOYEES_BY_SPECIFICATION_ID =
            "UPDATE employment SET jid = 0 WHERE jid IN (SELECT id FROM jobs WHERE sid = ?)";

    /**
     * This query change user interface language. <br />
     * Requires to set language and user id.
     */
    private static final String SQL_CHANGE_UI_LANGUAGE =
            "UPDATE users SET `language` = ? WHERE id = ?";

    /**
     * Return hashed user password
     *
     * @param login User mail
     * @return Hashed password
     * @throws DAOException object if execution of query is failed
     */
    public String getPassword(String login) throws DAOException{
        String password = "";
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_PASSWORD_BY_LOGIN);
            preparedStatement.setBytes(1, login.getBytes());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password =  resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_PASSWORD) + login, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_PASSWORD) + login);
        return password;
    }

    /**
     * Returns User object if user exist and password is correct
     *
     * @param email User mail
     * @param password user password
     * @return User object if user exist and password is correct
     * @throws DAOException object if execution of query is failed
     */
    public User getUser(String email, String password) throws DAOException{
        connector = new DBConnector();
        User user = null;
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_USER_BY_EMAIL_AND_PASSWORD);
            preparedStatement.setBytes(1, email.getBytes());
            preparedStatement.setBytes(2, password.getBytes());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User dbUser = new User();
                dbUser.setId(resultSet.getInt(1));
                dbUser.setLanguage(resultSet.getString(6));
                //dbUser.setQualification(resultSet.getInt(7));
                dbUser.setRole(getUserRole(dbUser.getId()));
                user = dbUser;
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_USER) + email, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_USER) + email);
        return user;
    }

    /**
     * Returns role object for user
     *
     * @param id User id
     * @return Role object
     * @throws DAOException object if execution of query is failed
     */
    public Role getUserRole(int id) throws DAOException{
        connector = new DBConnector();
        Role role = null;
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_USER_ROLE_BY_USER_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String value = resultSet.getString(1);
                role = RoleDefiner.defineRole(value);
            }
            if (role == null){
                throw new DatabaseDataException(ResourceManager.getProperty(DATA_ROLE_NOT_FOUND) + id);
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_USER_ROLE) + id, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_USER_ROLE) + id);
        return role;
    }

    /**
     * Return list of mails of users which have status free
     *
     * @param qualification Qualification name
     * @return List of mails of free users
     * @throws DAOException object if execution of query is failed
     */
    public List<String> getFreeUsersMailsByQualification(String qualification) throws DAOException {
        List<String> mails = new ArrayList<String>();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_USERS_MAILS_BY_QUALIFICATION_AND_FREE_STATUS);
            preparedStatement.setBytes(1, qualification.getBytes());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mails.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_USER_MAILS) + qualification, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_USER_MAILS) + qualification);
        return mails;
    }

    /**
     * This method sets job where employee will be busy
     *
     * @param jid Job id
     * @param mail User mail
     * @throws DAOException object if execution of query is failed
     */
    public void takeEmployee(int jid, String mail) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_TAKE_EMPLOYEE);
            preparedStatement.setInt(1, jid);
            preparedStatement.setBytes(2, mail.getBytes());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_TAKE_EMPLOYEE) + mail, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_TAKE_EMPLOYEE) + mail + "," + jid);
    }

    /**
     * This method checks employee status
     *
     * @param id User id
     * @return True if employee is free, otherwise false
     * @throws DAOException object if execution of query is failed
     */
    public boolean isEmployeeFree(int id) throws DAOException {
        boolean status = false;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_CHECK_STATUS);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) != 0) {
                    status = true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_IS_FREE_EMPLOYEE) + id, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_IS_FREE_EMPLOYEE) + id);
        return status;
    }

    /**
     * This method checks employee status using mail.
     *
     * @param mail Employee mail
     * @return True if employee is free, otherwise false
     * @throws DAOException object if execution of query is failed
     */
    public boolean isEmployeeFree(String mail) throws DAOException {
        boolean status = false;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_CHECK_STATUS_BY_MAIL);
            preparedStatement.setBytes(1, mail.getBytes());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) != 0) {
                    status = true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_IS_FREE_EMPLOYEE_MAIL) + mail, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_IS_FREE_EMPLOYEE_MAIL) + mail);
        return status;
    }

    /**
     * This method returns user mail
     *
     * @param id User id
     * @return User mail
     * @throws DAOException object if execution of query is failed
     */
    public String getUserMail(int id) throws DAOException {
        String mail = "";
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_USER_MAIL_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mail = resultSet.getString("mail");
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_USER_MAIL) + id, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_USER_MAIL) + id);
        return mail;
    }

    /**
     * This method returns mail list of employees which working on certain job
     *
     * @param jid Job id
     * @return List of mails
     * @throws DAOException object if execution of query is failed
     */
    public List<String> getEmployeeMailsWorkingOnJob(int jid) throws DAOException {
        List<String> mails = new ArrayList<>();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_EMPLOYEE_MAILS_BY_JOB_ID);
            preparedStatement.setInt(1, jid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                mails.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_WORKING_MAILS) + jid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_WORKING_MAILS) + jid);
        return mails;
    }

    /**
     * This method change employee status to free
     *
     * @param sid Specification id
     * @throws DAOException object if execution of query is failed
     */
    public void exemptEmployees(int sid) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_EXEMPT_EMPLOYEES_BY_SPECIFICATION_ID);
            preparedStatement.setInt(1, sid);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_EXEMPT_EMPLOYEES) + sid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_EXEMPT_EMPLOYEES) + sid);
    }

    public void changeUILanguage(String lang, int uid) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_CHANGE_UI_LANGUAGE);
            preparedStatement.setBytes(1, lang.getBytes());
            preparedStatement.setInt(2, uid);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("", e);
        } finally {
            connector.close();
        }
    }

}
