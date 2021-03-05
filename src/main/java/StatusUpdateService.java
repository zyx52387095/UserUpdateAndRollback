import model.Customer;
import model.Operators;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatusUpdateService {
    public StatusUpdateService(String dbUsername, String dbPassword) {
        new DBOperations(dbUsername, dbPassword);
    }

    /**
     * get all merchat users except 'suspended', 'deleted' or already 'email_sent'
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
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        System.out.println("size is "+customerStatusList.size());
        return customerStatusList;
    }

    /**
     * get all operator users except status 'deleted' and 'email_sent'
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
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        System.out.println("size is "+operatorStatusList.size());
        return operatorStatusList;
    }

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
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        System.out.println("size is "+customerStatusList.size());
        return customerStatusList;
    }



    public int updateMerchantStatus(String status, String customerId) {
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateaMerchantStatus)) {
            pstmt.setString(1, status);
            pstmt.setString(2, customerId);
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }
    public int updateOperatorStatus(String status, String customerId) {
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateOperatorStatus)) {
            pstmt.setString(1, status);
            pstmt.setString(2, customerId);
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }

    public int addStatusTrackingRecord(String oid,String currentStatus,String previousStatus,
                                       String type) {
        //generate a length 32 id randomly
        String generatedString = RandomStringUtils.random(32, true, true);
        System.out.println("generatedId is "+generatedString);

        //get the timestamp
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        System.out.println("time: "+currentTimestamp);
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlInsertStatusTacking)) {
            pstmt.setString(1, generatedString);
            pstmt.setString(2, oid);
            pstmt.setString(3, type);
            pstmt.setString(4, previousStatus);
            pstmt.setString(5, currentStatus);
            pstmt.setTimestamp(6, currentTimestamp);
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }



}
