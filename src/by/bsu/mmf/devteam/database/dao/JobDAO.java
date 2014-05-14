package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Job;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDAO extends AbstractDAO {
    private static final String SQL_GET_COUNT_JOBS_BY_SPECIFICATION_ID =
            "SELECT COUNT(id) FROM jobs WHERE sid = ?";

    private static final String SQL_FIND_JOBS_BY_SPECIFICATION_ID =
            "SELECT jobs.id, jobs.name, jobs.specialist, qualifications.qualification FROM jobs " +
            "INNER JOIN qualifications ON jobs.qualification = qualifications.id WHERE sid = ?";

    private static final String SQL_INSERT_JOB =
            "INSERT INTO jobs (sid, name, specialist, qualification) VALUES (?, ?, ?, ?)";

    private static final String SQL_SET_JOB_COST =
            "UPDATE jobs SET cost = ? WHERE id = ?";

    private static final String SQL_FIND_JOB_WHERE_BUSY_EMPLOYEE =
            "SELECT * FROM jobs WHERE id = (SELECT jid FROM employment WHERE uid = 6)";

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

    public void saveJob(int id, String name, int specialist, int qualification) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_INSERT_JOB);
            preparedStatement.setInt(1, id);
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

    public Job getJobWhereEmployeeBusy (int id) throws DAOException {
        Job job = new Job();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_JOB_WHERE_BUSY_EMPLOYEE);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                job.setId(resultSet.getInt("id"));
                job.setName(resultSet.getString("name"));
                job.setTime(resultSet.getInt("time"));
                job.setSpecification(resultSet.getInt("sid"));
            }
        } catch (SQLException e) {
            throw new DAOException(", ", e);
        } finally {
            connector.close();
        }
        return job;
    }

}
