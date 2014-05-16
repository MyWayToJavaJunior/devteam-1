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

public class UserDAO extends AbstractDAO {
    private static Logger logger = Logger.getLogger("db");

    private static final String SQL_FIND_PASSWORD_BY_LOGIN =
            "SELECT password FROM users WHERE mail = ?";

    private static final String SQL_FIND_USER_ROLE_BY_USER_ID =
            "SELECT role.name FROM users INNER JOIN role ON users.rid = role.id WHERE users.id = ?";

    private static final String SQL_FIND_USER_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM users WHERE mail = ? AND password = ?";

    private static final String SQL_FIND_EMPLOYEES_BY_QUALIFICATION =
            "SELECT * FROM users INNER JOIN qualifications ON users.qualification = qualifications.id" +
            "WHERE qualifications.qualification = ?";

    private static final String SQL_FIND_USERS_MAILS_BY_QUALIFICATION_AND_FREE_STATUS =
            "SELECT users.mail FROM users INNER JOIN qualifications ON users.qualification = qualifications.id " +
            "INNER JOIN employment ON  employment.uid = users.id " +
            "AND employment.jid = 0 AND qualifications.qualification = ?";

    private static final String SQL_TAKE_EMPLOYEE =
            "UPDATE employment SET jid = ? WHERE uid = (SELECT id FROM users WHERE mail = ?)";

    private static final String SQL_CHECK_STATUS =
            "SELECT jid FROM employment WHERE uid = ?";

    private static final String SQL_FIND_USER_MAIL_BY_ID =
            "SELECT mail FROM users WHERE id = ?";

    private static final String SQL_FIND_EMPLOYEE_MAILS_BY_JOB_ID =
            "SELECT mail FROM users WHERE id IN (SELECT uid FROM employment WHERE jid = ?)";

    private static final String SQL_EXEMPT_EMPLOYEES_BY_SPECIFICATION_ID =
            "UPDATE employment SET jid = 0 WHERE jid IN (SELECT id FROM jobs WHERE sid = ?)";

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

    public void takeEmployee(int id, String mail) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_TAKE_EMPLOYEE);
            preparedStatement.setInt(1, id);
            preparedStatement.setBytes(2, mail.getBytes());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

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
