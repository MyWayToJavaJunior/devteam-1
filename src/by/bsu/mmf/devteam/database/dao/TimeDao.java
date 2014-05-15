package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;

import java.sql.SQLException;

public class TimeDao extends AbstractDAO {
    private static final String SQL_SET_ELAPSED_TIME =
            "INSERT INTO elapsed (jid, uid, time) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE_EXISTING_ELAPSED_TIME =
            "UPDATE elapsed SET time = ? WHERE uid = ? AND jid = ?";

    private static final String SQL_FIND_EXISTING_ELAPSED_TIME =
            "SELECT * FROM elapsed WHERE jid = ? AND uid = ?";

    private static final String SQL_IS_EXIST_ELAPSED_TIME =
            "SELECT * FROM elapsed WHERE jid = ? AND uid = ?";

    public void saveElapsedTime(int uid, int jid, int time) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_SET_ELAPSED_TIME);
            preparedStatement.setInt(1, jid);
            preparedStatement.setInt(2, uid);
            preparedStatement.setInt(3, time);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

    public void updateElapsedTime(int uid, int jid, int time) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_UPDATE_EXISTING_ELAPSED_TIME);
            preparedStatement.setInt(1, time);
            preparedStatement.setInt(2, uid);
            preparedStatement.setInt(3, jid);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

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
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return time;
    }

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
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return exist;
    }

}
