package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements DAO pattern.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class JobDAO extends AbstractDAO {
    /* Initializing database activity logger */
    private static Logger logger = Logger.getLogger("db");

    /**
     * Keeps query which return the number of jobs in specification. <br />
     * Requires to set specification id.
     */
    private static final String SQL_GET_COUNT_JOBS_BY_SPECIFICATION_ID =
            "SELECT COUNT(id) FROM jobs WHERE sid = ?";

    /**
     * Keeps query which return all jobs from certain specification. <br />
     * Requires to set specification id.
     */
    private static final String SQL_FIND_JOBS_BY_SPECIFICATION_ID =
            "SELECT jobs.id, jobs.name, jobs.specialist, qualifications.qualification FROM jobs " +
            "INNER JOIN qualifications ON jobs.qualification = qualifications.id WHERE sid = ?";

    /**
     * Keeps query save new job in database. <br />
     * Requires to set: specification id, name of job, number of specislist, <br />
     * qualification of specialist required for job.
     */
    private static final String SQL_INSERT_JOB =
            "INSERT INTO jobs (sid, name, specialist, qualification) VALUES (?, ?, ?, ?)";

    /**
     * Keeps query which sets cost for job. <br />
     * Requires to set cost and job id.
     */
    private static final String SQL_SET_JOB_COST =
            "UPDATE jobs SET cost = ? WHERE id = ?";

    /**
     * Keeps query which return job where employee busy. <br />
     * Requires to set user id.
     */
    private static final String SQL_FIND_JOB_WHERE_BUSY_EMPLOYEE =
            "SELECT * FROM jobs WHERE id = (SELECT jid FROM employment WHERE uid = ?)";

    /**
     * Return number of jobs in specification created by customer
     *
     * @param id Specification id
     * @return Number of jobs in specification
     * @throws DAOException object if execution of query is failed
     */
    public int getNumberOfJobsInSpecification(int id) throws DAOException{
        connector = new DBConnector();
        int jobs = 0;
        try {
            PreparedStatement statement = connector.getPreparedStatement(SQL_GET_COUNT_JOBS_BY_SPECIFICATION_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                jobs = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getSQLState(), e);
        } finally {
            connector.close();
        }
        return jobs;
    }

    /**
     * Returns list of jobs in certain specification
     *
     * @param id Specification id
     * @return List of Jobs
     * @throws DAOException object if execution of query is failed
     */
    public List<Job> getSpecificationJobs(int id) throws DAOException {
        List<Job> jobs = new ArrayList<Job>();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_JOBS_BY_SPECIFICATION_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Job job = new Job();
                job.setId(resultSet.getInt(1));
                job.setName(resultSet.getString(2));
                job.setSpecialist(resultSet.getInt(3));
                job.setQualification(resultSet.getString(4));
                jobs.add(job);
            }
        } catch (SQLException e) {
            throw new DAOException(".", e);
        } finally {
            connector.close();
        }
        return jobs;
    }

    /**
     * This method for save new job in database
     *
     * @param sid Specification id
     * @param name Name of job
     * @param specialist Number of specialists required for job
     * @param qualification Qualification of required specialists
     * @throws DAOException object if execution of query is failed
     */
    public void saveJob(int sid, String name, int specialist, int qualification) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_INSERT_JOB);
            preparedStatement.setInt(1, sid);
            preparedStatement.setBytes(2, name.getBytes());
            preparedStatement.setInt(3, specialist);
            preparedStatement.setInt(4, qualification);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

    /**
     * This method saves cost which defines the manager of project
     * @param id Job id
     * @param cost Cost of job
     * @throws DAOException object if execution of query is failed
     */
    public void setJobCost(int id, int cost) throws DAOException{
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_SET_JOB_COST);
            preparedStatement.setInt(1, cost);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

    /**
     * Return Job object in which employee busy
     *
     * @param id Job id
     * @return Job object
     * @throws DAOException object if execution of query is failed
     */
    public Job getJobWhereEmployeeBusy (int id) throws DAOException {
        Job job = new Job();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_JOB_WHERE_BUSY_EMPLOYEE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                job.setId(resultSet.getInt(1));
                job.setSpecification(resultSet.getInt(2));
                job.setName(resultSet.getString(3));
                job.setTime(resultSet.getInt(6));
            }
        } catch (SQLException e) {
            throw new DAOException(", ", e);
        } finally {
            connector.close();
        }
        return job;
    }

}
