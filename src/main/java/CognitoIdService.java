import model.OidBackUp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CognitoIdService {

    public CognitoIdService(String dbUsername, String dbPassword) {
        new DBOperations(dbUsername, dbPassword);
    }


    public int updateCognitoID(String CognitoId, String customerId) {
        int affectedRows = 0;
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateCognitoID)) {
            pstmt.setString(1, CognitoId);
            pstmt.setString(2, customerId);
            affectedRows = pstmt.executeUpdate();
            System.out.println("affectedRows? "+affectedRows);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedRows;
    }

    //get original id and put into json file for backup
    public OidBackUp getBackUpJsonForOId(String pr_identity) {
        OidBackUp oidBackUp = null;

        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.getOIdandPrIdentity)) {
            pstmt.setString(1, pr_identity);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                oidBackUp = new OidBackUp(rs.getString("oid"),
                        rs.getString("pr_identity"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return oidBackUp;

    }

    public String getMerchantIdByUserName(String userName) {

        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlGetMerchantId)) {
            pstmt.setString(1, userName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public String getOperatorIdByUserName(String userName) {
        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlGetOperatorId)) {
            pstmt.setString(1, userName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                return rs.getString("id");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
