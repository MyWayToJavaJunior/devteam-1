package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.data.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Project;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements DAO pattern
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class ProjectDAO extends AbstractDAO {
    /** Initializing database activity logger */
    private static Logger logger = Logger.getLogger("db");

    /** Logger messages */
    private static final String ERROR_GET_MANAGER_PROJECTS = "logger.db.error.get.manager.projects";
    private static final String INFO_GET_MANAGER_PROJECTS = "logger.db.info.get.manager.projects";
    private static final String ERROR_SAVE_PROJECT = "logger.db.error.save.project";
    private static final String INFO_SAVE_PROJECT = "logger.db.info.save.project";
    private static final String ERROR_GET_PROJECT = "logger.db.error.get.project";
    private static final String INFO_GET_PROJECT = "logger.db.info.get.project";
    private static final String ERROR_GET_PROJECT_BY_ID = "logger.db.error.get.project.by.id";
    private static final String INFO_GET_PROJECT_BY_ID = "logger.db.info.get.project.by.id";
    private static final String ERROR_GET_LAST_PROJECT_ID = "logger.db.error.get.last.project.id";
    private static final String INFO_GET_LAST_PROJECT_ID = "logger.db.info.get.last.project.id";

    /**
     * Keeps query which return project created by certain manager. <br />
     * Requires to set manager id.
     */
    private static final String SQL_FIND_MANAGER_PROJECTS =
            "SELECT * FROM projects WHERE manager = ?";

    /**
     * Keeps query which saves new project in database. <br />
     * Requires to set: project name, manager is, specification id.
     */
    private static final String SQL_SAVE_PROJECT =
            "INSERT INTO projects (name, manager, sid) VALUES (?, ?, ?)";

    /**
     * Keeps query which return project created for certain specification. <br />
     * Requires to set specification id.
     */
    private static final String SQL_FIND_PROJECT_BY_SPECIFICATION_ID =
            "SELECT * FROM projects WHERE sid = ?";

    /**
     * Keeps query which searches last project created by certain manager. <br />
     * Requires to set manager id.
     */
    private static final String SQL_FIND_LAST_MANAGER_PROJECT_ID =
            "SELECT id FROM projects WHERE manager = ? ORDER BY id DESC LIMIT 1 ";

    /**
     * Keeps query which searches project by specification id.
     */
    private static final String SQL_FIND_PROJECT_BY_PROJECT_ID =
            "SELECT * FROM projects WHERE id = ?";

    /**
     * This method returns list of all manager projects.
     *
     * @param id Manager id
     * @return List of projects
     * @throws DAOException object if execution of query is failed
     */
    public List<Project> getManagerProjects(int id) throws DAOException {
        List<Project> list = new ArrayList<Project>();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_MANAGER_PROJECTS);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt(1));
                project.setName(resultSet.getString(2));
                project.setSpecification(resultSet.getInt(4));
                list.add(project);
            }
        } catch (SQLException exception) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_MANAGER_PROJECTS) + id, exception);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_MANAGER_PROJECTS) + id);
        return list;
    }

    /**
     * This method saves new project in database.
     *
     * @param name Project name
     * @param mid Manager id
     * @param sid Specification id
     * @return Id of saved project
     * @throws DAOException object if execution of query is failed
     */
    public int saveProject(String name, int mid, int sid) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_SAVE_PROJECT);
            preparedStatement.setBytes(1, new String(name.getBytes("ISO-8859-1"), "CP1251").getBytes());
            preparedStatement.setInt(2, mid);
            preparedStatement.setInt(3, sid);
            preparedStatement.execute();
        } catch (SQLException | UnsupportedEncodingException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_SAVE_PROJECT) + name, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_SAVE_PROJECT) + sid);
        return getLastProjectId(mid);
    }

    /**
     * This method returns project by specification id.
     *
     * @param sid Specification id
     * @return Project object
     * @throws DAOException object if execution of query is failed
     */
    public Project getProject(int sid) throws DAOException {
        Project project = new Project();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_PROJECT_BY_SPECIFICATION_ID);
            preparedStatement.setInt(1, sid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setManager(resultSet.getInt("manager"));
                project.setSpecification(resultSet.getInt("sid"));
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_PROJECT) + sid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_PROJECT) + sid);
        return project;
    }

    /**
     * This method returns project object by project id.
     *
     * @param pid Project id
     * @return Project object
     * @throws DAOException object if execution of query is failed
     */
    public Project getProjectById(int pid) throws DAOException {
        Project project = new Project();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_PROJECT_BY_PROJECT_ID);
            preparedStatement.setInt(1, pid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setManager(resultSet.getInt("manager"));
                project.setSpecification(resultSet.getInt("sid"));
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_PROJECT_BY_ID) + pid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_PROJECT_BY_ID) + pid);
        return project;
    }

    /**
     * This method returns last created project by certain manager.
     *
     * @param mid Manager id
     * @return Last created project id created by manager
     * @throws DAOException object if execution of query is failed
     */
    public int getLastProjectId(int mid) throws DAOException {
        int id = 0;
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_LAST_MANAGER_PROJECT_ID);
            preparedStatement.setInt(1, mid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_LAST_PROJECT_ID) + mid, e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_LAST_PROJECT_ID) + mid);
        return id;
    }

}
