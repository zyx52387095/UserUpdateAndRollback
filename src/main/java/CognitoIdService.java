import model.OidBackUp;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class CognitoIdService {
    private static final Logger LOGGER = Logger.getLogger( CognitoIdService.class.getName() );

    public CognitoIdService(String dbUsername, String dbPassword) {
        new DBOperations(dbUsername, dbPassword);
        FileHandler fh;
        try {
            fh = new FileHandler("IdUpdate.log");
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update oid with Cognito Id getting from the json file generated.
     * @param CognitoId Cognito id which is replacing oid
     * @param customerId user id
     * @return affected rows
     */

    public int updateCognitoID(String CognitoId, String customerId) {
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateCognitoID)) {
            pstmt.setString(1, CognitoId);
            pstmt.setString(2, customerId);
            affectedRows = pstmt.executeUpdate();
            LOGGER.info("CognitoId updatedto: "+CognitoId+" for customer: "+customerId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
        LOGGER.info("Affected rows: " + affectedRows);
        return affectedRows;
    }

    /**
     * Get the original oid for users
     * @param pr_identity user id, which is also the foreign key of oid
     * @return k-v object that contains user id and oid
     */
    //get original id and put into json file for backup
    public OidBackUp getBackUpJsonForOId(String pr_identity) {
        OidBackUp oidBackUp = null;

        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.getOIdandPrIdentity)) {
            pstmt.setString(1, pr_identity);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                oidBackUp = new OidBackUp(rs.getString("oid"),
                        rs.getString("pr_identity"));
                LOGGER.info("Saved to json "+rs.getString("oid")+" "+rs.getString("pr_identity"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }

        return oidBackUp;

    }

    /**
     * Get merchant user id by email
     * @param userName email value from the Cognito Json file
     * @return user id
     */
    public String getMerchantIdByUserName(String userName) {
        LOGGER.info("Getting id for Merchant user " + userName);
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlGetMerchantId)) {
            pstmt.setString(1, userName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                LOGGER.info("id for Merchant " + userName + " id is " + rs.getString("id"));
                return rs.getString("id");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
    }

    /**
     * Get operator user id by email
     * @param userName email value from the Cognito Json file
     * @return user id
     */
    public String getOperatorIdByUserName(String userName) {
        LOGGER.info("Getting id for Operator user" + userName);
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlGetOperatorId)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                LOGGER.info("Id is " + rs.getString("id"));
                return rs.getString("id");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }

    }
}
