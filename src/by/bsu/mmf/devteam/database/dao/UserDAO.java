package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.exception.infrastructure.DatabaseDataException;
import by.bsu.mmf.devteam.logic.bean.user.Role;
import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.user.RoleDefiner;
import by.bsu.mmf.devteam.logic.bean.user.User;
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
    /* Initializing database activity logger */
    private static Logger logger = Logger.getLogger("db");

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
     * Return hashed user password
     *
     * @param login User mail
     * @return Hashed password
     * @throws DAOException object if execution of query is failed
     */
    public String getPassword(String login) throws DAOException{
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_PASSWORD_BY_LOGIN);
            preparedStatement.setBytes(1, login.getBytes());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.close();
        }
        return "";
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
            throw new DAOException("SQL error when searching a user by name and password", e);
        } finally {
            connector.close();
        }
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
                throw new DatabaseDataException("Role for user: " + id + " not found.");
            }
        } catch (SQLException exception) {
            throw new DAOException("", exception);
        } finally {
            connector.close();
        }
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
        } catch (SQLException exception) {
            throw new DAOException(".", exception);
        } finally {
            connector.close();
        }
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
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
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
            throw new DAOException(", ", e);
        } finally {
            connector.close();
        }
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
            throw new DAOException(", ", e);
        } finally {
            connector.close();
        }
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
        } catch (SQLException exception) {
            throw new DAOException(".", exception);
        } finally {
            connector.close();
        }
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
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

}
