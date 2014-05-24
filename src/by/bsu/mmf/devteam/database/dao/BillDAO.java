package by.bsu.mmf.devteam.database.dao;

import by.bsu.mmf.devteam.database.connector.DBConnector;
import by.bsu.mmf.devteam.exception.data.ResourceDataException;
import by.bsu.mmf.devteam.exception.infrastructure.DAOException;
import by.bsu.mmf.devteam.logic.bean.entity.Bill;
import by.bsu.mmf.devteam.resource.ResourceManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements DAO pattern and contains methods for work with customer bills.
 *
 * @author Dmitry Petrovich
 * @since 1.0.0-alpha
 */
public class BillDAO extends AbstractDAO {
    /** Initializes logger */
    private static Logger logger = Logger.getLogger("db");

    /** Logger messages */
    private static final String ERROR_GET_CUSTOMER_BILLS = "logger.db.error.get.customer.bills";
    private static final String INFO_GET_CUSTOMER_BILLS = "logger.db.info.get.customer.bills";
    private static final String ERROR_GET_MANAGER_BILLS = "logger.db.error.get.manager.bills";
    private static final String INFO_GET_MANAGER_BILLS = "logger.db.info.get.manager.bills";
    private static final String ERROR_GET_LAST_BILL_NAME = "logger.db.error.get.last.bill.name";
    private static final String ERROR_CREATE_BILL = "logger.db.error.create.bill";
    private static final String INFO_CREATE_BILL = "logger.db.info.create.bill";

    /**
     * Keeps query which return customer bills order by newest. <br />
     * Requires to set customer id.
     */
    public static final String SQL_FIND_BILLS_FOR_CUSTOMER =
            "SELECT * FROM bills WHERE cid = ? ORDER BY id DESC";

    /**
     * Keeps query which return customer bills order by newest. <br />
     * Requires to set who created (manager id).
     */
    public static final String SQL_FIND_ALL_BILLS_CREATED_BY =
            "SELECT * FROM bills WHERE mid = ? ORDER BY id DESC";

    /**
     * Keeps query which return name of last added bill.
     */
    public static final String SQL_FIND_LAST_BILL =
            "SELECT name FROM bills ORDER BY id DESC LIMIT 1";

    /**
     * Keeps query which saves new bill in database. <br />
     * Requires to set bill name
     */
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
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_CUSTOMER_BILLS), e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_CUSTOMER_BILLS) + id);
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
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_MANAGER_BILLS), e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_GET_MANAGER_BILLS) + id);
        return bills;
    }

    /**
     * This method returns the last created bill name
     *
     * @return Name of last created bill
     * @throws DAOException object if execution of query is failed
     */
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
            throw new DAOException(ResourceManager.getProperty(ERROR_GET_LAST_BILL_NAME), e);
        } finally {
            connector.close();
        }
        return name;
    }

    /**
     * This method saves new bill in database
     *
     * @param name Name of new bill
     * @param cid Customer id from table `users`
     * @param pid Project id from table `projects`
     * @param mid Manager id from table `users`
     * @param sum Total cost for payment
     * @throws DAOException object if execution of query is failed
     */
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
            throw new DAOException(ResourceManager.getProperty(ERROR_CREATE_BILL), e);
        } finally {
            connector.close();
        }
        logger.info(ResourceManager.getProperty(INFO_CREATE_BILL) + name);
    }

}
