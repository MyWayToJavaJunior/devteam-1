package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Bill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAO extends AbstractDAO {
    public static final String SQL_FIND_BILLS_FOR_CUSTOMER =
            "SELECT * FROM bills WHERE cid = ? ORDER BY id DESC";

    public static final String SQL_FIND_ALL_BILLS_CREATED_BY =
            "SELECT * FROM bills WHERE mid = ? ORDER BY id DESC";

    public static final String SQL_FIND_CUSTOMER_BILLS_CREATED_BY =
            "SELECT * FROM bills WHERE cid = ? AND pid = ?  ORDER BY id DESC";

    public static final String SQL_CREATE_BILL_FOR_CUSTOMER =
            "INSERT INTO bills (name, cid, pid, mid, sum) VALUES ('?', ?, ?, ?, ?)";

    public List<Bill> getCustomerBills(int id) throws DAOException{
        connector = new DBConnector();
        List<Bill> bills = new ArrayList<Bill>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(SQL_FIND_BILLS_FOR_CUSTOMER);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt(1));
                bill.setName(resultSet.getString(2));
                bill.setCid(resultSet.getInt(3));
                bill.setPid(resultSet.getInt(4));
                bill.setMid(resultSet.getInt(5));
                bill.setSum(resultSet.getInt(6));
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new DAOException("SQl error when trying to get list of customer bills.", e);
        } finally {
            connector.close();
        }
        return bills;
    }

    public List<Bill> getManagerBills(int id) throws DAOException{
        connector = new DBConnector();
        List<Bill> bills = new ArrayList<Bill>();
        try {
            PreparedStatement statement = connector.getPreparedStatement(SQL_FIND_ALL_BILLS_CREATED_BY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill();
                bill.setId(resultSet.getInt(1));
                bill.setName(resultSet.getString(2));
                bill.setCid(resultSet.getInt(3));
                bill.setPid(resultSet.getInt(4));
                bill.setMid(resultSet.getInt(5));
                bill.setSum(resultSet.getInt(6));
                bills.add(bill);
            }
        } catch (SQLException e) {
            throw new DAOException("SQl error when trying to get list of manager bills.", e);
        } finally {
            connector.close();
        }
        return bills;
    }

}
