package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Bill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements DAO pattern and contains methods for work with customer bills.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0
 */
public class BillDAO extends AbstractDAO {
    /**
     *  Keeps query which return customer bills order by newest. <br />
     *  Requires to set customer id.
     */
    public static final String SQL_FIND_BILLS_FOR_CUSTOMER =
            "SELECT * FROM bills WHERE cid = ? ORDER BY id DESC";

    /**
     *  Keeps query which return customer bills order by newest. <br />
     *  Requires to set who created (manager id).
     */
    public static final String SQL_FIND_ALL_BILLS_CREATED_BY =
            "SELECT * FROM bills WHERE mid = ? ORDER BY id DESC";

    public static final String SQL_FIND_LAST_BILL =
            "SELECT name FROM bills ORDER BY id DESC LIMIT 1";

    /*  */
    public static final String SQL_FIND_CUSTOMER_BILLS_CREATED_BY =
            "SELECT * FROM bills WHERE cid = ? AND pid = ?  ORDER BY id DESC";

    /*  */
    public static final String SQL_CREATE_BILL_FOR_CUSTOMER =
            "INSERT INTO bills (name, cid, pid, mid, sum) VALUES (?, ?, ?, ?, ?)";

    /**
     * Returns list of all bills for customer
     *
     * @param id customer id
     * @return List of bills
     * @throws DAOException object if execution of query is failed
     */
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

    /**
     * Returns list of bills created by manager
     *
     * @param id manager id
     * @return List of bills
     * @throws DAOException object if execution of query is failed
     */
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

    public String getLastBillName() throws DAOException {
        String name = "";
        connector = new DBConnector();
        try {
            statement = connector.getStatement();
            resultSet = statement.executeQuery(SQL_FIND_LAST_BILL);
            if (resultSet.next()) {
                if (resultSet.getObject(1) != null) {
                    name = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
        return name;
    }

    public void createBill(String name, int cid, int pid, int mid, int sum) throws DAOException {
        connector = new DBConnector();
        try {
            preparedStatement = connector.getPreparedStatement(SQL_CREATE_BILL_FOR_CUSTOMER);
            preparedStatement.setBytes(1, name.getBytes());
            preparedStatement.setInt(2, cid);
            preparedStatement.setInt(3, pid);
            preparedStatement.setInt(4, mid);
            preparedStatement.setInt(5, sum);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException(",", e);
        } finally {
            connector.close();
        }
    }

}
