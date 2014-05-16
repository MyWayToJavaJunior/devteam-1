package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Specification;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecificationDAO extends AbstractDAO {
    /* Initializing database activity logger */
    private static Logger logger = Logger.getLogger("db");

    /* Keeps default specification status [waiting] */
    private static final String DEFAULT_SPECIFICATION_STATUS = "waiting";

    /* Query finding  */
    public static final String SQL_FIND_SPECIFICATION_NAME_BY_ID =
            "SELECT name FROM specifications WHERE id = ?";

    /*  */
    public static final String SQL_FIND_SPECIFICATION_STATUS_BY_ID =
            "SELECT status FROM specifications WHERE id = ?";

    /* Select specifications created by customer */
    public static final String SQL_FIND_SPECIFICATIONS_BY_CUSTOMER_ID =
            "SELECT * FROM specifications WHERE uid=? ORDER BY id DESC";

    /* Select waiting specifications from all customers */
    private static final String SQL_FIND_WAITING_SPECIFICATIONS =
            "SELECT specifications.id, specifications.uid, specifications.name, specifications.`status`, " +
            "users.id, users.mail FROM specifications " +
            "INNER JOIN users ON specifications.uid = users.id " +
            "WHERE status = ? ORDER BY specifications.id ASC";

    /* Set specification status to processing */
    public static final String SQL_SET_SPECIFICATION_STATUS_TO =
            "UPDATE specifications SET status = ? WHERE id = ?";

    /* Save new customer specification */
    public static final String SQL_INSERT_NEW_SPECIFICATION =
            "INSERT INTO specifications (uid, name, status) VALUES (?, ?, ?)";

    /*  */
    public static final String SQL_FIND_LAST_CUSTOMER_SPECIFICATION_ID =
            "SELECT MAX(id) FROM specifications WHERE uid = ?";

    public static final String SQL_FIND_USER_ID_BY_SPECIFICATION_ID =
            "SELECT uid FROM specifications WHERE id = ?";

    public String getSpecificationName(int id) throws DAOException {
        String name = "";
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_SPECIFICATION_NAME_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                name = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(".", e);
        } finally {
            connector.close();
        }
        return name;
    }

    public String getSpecificationStatus(int id) throws DAOException {
        String status = "";
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_SPECIFICATION_STATUS_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                status = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(".", e);
        } finally {
            connector.close();
        }
        return status;
    }

    public List<Specification> getUserSpecifications(int id) throws DAOException{
        connector = new DBConnector();
        List<Specification> list = new ArrayList<Specification>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(SQL_FIND_SPECIFICATIONS_BY_CUSTOMER_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setId(resultSet.getInt(1));
                specification.setName(resultSet.getString(3));
                specification.setStatus(resultSet.getString(4));
                JobDAO jobsDAO = new JobDAO();
                specification.setJobs(jobsDAO.getNumberOfJobsInSpecification(resultSet.getInt(1)));
                list.add(specification);
            }
        } catch (SQLException e) {
            throw new DAOException("Get user specifications failed", e);
        } finally {
            connector.close();
        }
        return list;
    }

    public List<Specification> getWaitingSpecifications() throws DAOException{
        connector = new DBConnector();
        List<Specification> list = new ArrayList<>();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_WAITING_SPECIFICATIONS);
            preparedStatement.setBytes(1, DEFAULT_SPECIFICATION_STATUS.getBytes());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Specification spec = new Specification();
                spec.setId(resultSet.getInt(1));
                spec.setEmail(resultSet.getString(6));
                spec.setName(resultSet.getString(3));
                JobDAO jobsDAO = new JobDAO();
                spec.setJobs(jobsDAO.getNumberOfJobsInSpecification(resultSet.getInt(1)));
                list.add(spec);
            }
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return list;
    }

    public int saveSpecification(int id, String name) throws DAOException{
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_INSERT_NEW_SPECIFICATION);
            preparedStatement.setInt(1, id);
            preparedStatement.setBytes(2, name.getBytes());
            preparedStatement.setBytes(3, DEFAULT_SPECIFICATION_STATUS.getBytes());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(".", e);
        } finally {
            connector.close();
        }
        return getLastSpecificationId(id);
    }

    public int getLastSpecificationId(int userId) throws DAOException{
        connector = new DBConnector();
        int id = 0;
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_LAST_CUSTOMER_SPECIFICATION_ID);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return id;
    }

    public void setSpecificationStatus(int id, String status) throws DAOException{
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_SET_SPECIFICATION_STATUS_TO);
            preparedStatement.setBytes(1, status.getBytes());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

    public int getUserId(int sid) throws DAOException {
        int id = 0;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_USER_ID_BY_SPECIFICATION_ID);
            preparedStatement.setInt(1, sid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return id;
    }

}
