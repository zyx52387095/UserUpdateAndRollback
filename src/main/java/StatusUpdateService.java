import model.Customer;
import model.Operators;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class StatusUpdateService {
    private static final Logger LOGGER = Logger.getLogger(StatusUpdateService.class.getName());
    public StatusUpdateService(String dbUsername, String dbPassword) {
        new DBOperations(dbUsername, dbPassword);
        FileHandler fh;
        try {
            fh = new FileHandler("StatusUpdate.log");
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get all merchat users except 'suspended', 'deleted' or already 'email_sent'
     *
     * @return list of customers
     */
    public List<Customer> getyAllMerchantUsers() {
        List<Customer> customerStatusList = new ArrayList<>();
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlSelectAllMerchants)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                Customer customer = new Customer(rs.getString("id"),
                        rs.getString("customer_status"));
                System.out.println(customer.getId());
                System.out.println(customer.getStatus());
                customerStatusList.add(customer);
                LOGGER.info("Added merchant user to list: " + rs.getString("id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
        LOGGER.info("There are " + customerStatusList.size() + " users to be added");
        return customerStatusList;
    }

    /**
     * get all operator users except status 'deleted' and 'email_sent'
     *
     * @return
     */
    public List<Operators> getAllOperatorUsers() {
        List<Operators> operatorStatusList = new ArrayList<>();
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlSelectAllOperators)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                Operators operators = new Operators(rs.getString("id"),
                        rs.getString("status"));
                System.out.println(operators.getId());
                System.out.println(operators.getStatus());
                operatorStatusList.add(operators);
                LOGGER.info("Added operator user to list: " + rs.getString("id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
        LOGGER.info("There are " + operatorStatusList.size() + " users to be added");
        return operatorStatusList;
    }

    /**
     * Get all suspended users
     *
     * @return number of rows affected
     */
    public List<Customer> getAllSuspendedUsers() {
        List<Customer> customerStatusList = new ArrayList<>();
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlSelectAllSuspended)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                Customer customer = new Customer(rs.getString("id"),
                        rs.getString("customer_status"));
                System.out.println(customer.getId());
                System.out.println(customer.getStatus());
                customerStatusList.add(customer);
                LOGGER.info("Added suspended user to list: " + rs.getString("id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
        System.out.println("size is " + customerStatusList.size());
        LOGGER.info("There are " + customerStatusList.size() + " users to be added");
        return customerStatusList;
    }


    /**
     * Update user status for Merchant user
     *
     * @param status     new status
     * @param customerId id for the merchant user to be updated
     * @return rows affected
     */
    public int updateMerchantStatus(String status, String customerId) {
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateaMerchantStatus)) {
            pstmt.setString(1, status);
            pstmt.setString(2, customerId);
            LOGGER.info("Updating merchant user status " + customerId);
            affectedRows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        LOGGER.info("Affected rows: " + affectedRows);
        return affectedRows;

    }

    /**
     * Update user status for Operator user
     *
     * @param status     new status
     * @param customerId id for the merchant user to be updated
     * @return rows affected
     */
    public int updateOperatorStatus(String status, String customerId) {
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateOperatorStatus)) {
            pstmt.setString(1, status);
            pstmt.setString(2, customerId);
            LOGGER.info("Updating operator user status " + customerId);
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        LOGGER.info("Affected rows: " + affectedRows);
        return affectedRows;
    }

    /**
     * Add a row to status_tracking table for suspended users
     *
     * @param id             user id
     * @param currentStatus  current status, should always be 'suspended'
     * @param previousStatus previous status, should always be 'Email Send'
     * @param type           user type, should always be 'customer', because operators should not be suspeneded
     * @return rows affected
     */

    public int addStatusTrackingRecord(String id, String currentStatus, String previousStatus,
                                       String type) {
        //generate a length 32 id randomly
        String generatedString = RandomStringUtils.random(32, true, true);
        System.out.println("generatedId is " + generatedString);

        //get the timestamp
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        System.out.println("time: " + currentTimestamp);
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlInsertStatusTacking)) {
            pstmt.setString(1, generatedString);
            pstmt.setString(2, id);
            pstmt.setString(3, type);
            pstmt.setString(4, previousStatus);
            pstmt.setString(5, currentStatus);
            pstmt.setTimestamp(6, currentTimestamp);
            LOGGER.info("Inserting into tracking table for suspended user " + id);
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        LOGGER.info("Affected rows: " + affectedRows);
        return affectedRows;
    }


}
