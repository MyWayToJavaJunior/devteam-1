package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends AbstractDAO {
    private static final String SQL_FIND_MANAGER_PROJECTS =
            "SELECT * FROM projects WHERE manager = ?";

    private static final String SQL_SAVE_PROJECT =
            "INSERT INTO projects (name, manager, sid) VALUES (?, ?, ?)";

    private static final String SQL_FIND_PROJECT_BY_SPECIFICATION_ID =
            "SELECT * FROM projects WHERE sid = ?";

    private static final String SQL_FIND_LAST_MANAGER_PROJECT_ID =
            "SELECT id FROM projects WHERE manager = ? ORDER BY id DESC LIMIT 1 ";

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
            throw new DAOException(".", exception);
        } finally {
            connector.close();
        }
        return list;
    }

    public int saveProject(String name, int mid, int sid) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_SAVE_PROJECT);
            preparedStatement.setBytes(1, name.getBytes());
            preparedStatement.setInt(2, mid);
            preparedStatement.setInt(3, sid);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return getLastProjectId(mid);
    }

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
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return project;
    }

    public Project getProjectById(int pid) throws DAOException {
        Project project = new Project();
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_FIND_PROJECT_BY_SPECIFICATION_ID);
            preparedStatement.setInt(1, pid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setManager(resultSet.getInt("manager"));
                project.setSpecification(resultSet.getInt("sid"));
            }
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return project;
    }

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
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return id;
    }

}
