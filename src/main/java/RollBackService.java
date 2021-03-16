import model.Customer;
import model.OidBackUp;
import model.Operators;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RollBackService {
//    String dbUserName;
//    String dbPassWord;
//    public RollBackService(String dbUsername, String dbPassword) {
//        this.dbUserName = dbUsername;
//        this.dbPassWord = dbPassword;
//        new DBOperations(dbUsername, dbPassword);
//    }
//    //todo roll back oid
//    public void rollBackOid() {
//        JsonRW jsonRW = new JsonRW();
//        List<OidBackUp> oidBackUpList = jsonRW.readOidBackUpFromfile("OidBackUp.json");
//        for (int i = 0; i < oidBackUpList.size(); i++) {
//            String oilBackup = oidBackUpList.get(i).getoId();
//            String pr_identity = oidBackUpList.get(i).getPr_identity();
//            updateOidBack(oilBackup, pr_identity);
//        }
//    }
//    public int updateOidBack(String backupId, String customerId) {
//        int affectedRows = 0;
//        try (PreparedStatement pstmt = DBOperations.conn.prepareStatement(DBOperations.sqlUpdateCognitoID)) {
//            pstmt.setString(1, backupId);
//            pstmt.setString(2, customerId);
//            affectedRows = pstmt.executeUpdate();
//            System.out.println("affectedRows? "+affectedRows);
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return affectedRows;
//    }
//
//    //todo roll back status
//    public void rollBackMerchantStatus() {
//        JsonRW jsonRW = new JsonRW();
//        StatusUpdateService statusUpdateService = new StatusUpdateService(dbUserName, dbPassWord);
//        List<Customer> merBackUpList = jsonRW.readMerStatusBackUpFromfile("MerchantStatusBackUp.json");
//        System.out.println("merchant backup file size is "+merBackUpList.size());
//        for (int i = 0; i < merBackUpList.size(); i++) {
//            //get status and id from file
//            String id = merBackUpList.get(i).getId();
//            String status = merBackUpList.get(i).getStatus();
//            System.out.println("roll back " + id);
//            System.out.println("status is "+ status);
//
//            //update status back to database
//            int j = statusUpdateService.updateMerchantStatus(status, id);
//
//        }
//
//    }
//    public void rollBackOperatorStatus() {
//        JsonRW jsonRW = new JsonRW();
//        StatusUpdateService statusUpdateService = new StatusUpdateService(dbUserName, dbPassWord);
//        List<Operators> optBackUpList = jsonRW.readOptStatusBackUpFromfile("OperatorStatusBackUp.json");
//        System.out.println("operator backup file size is "+optBackUpList.size());
//        for (int i = 0; i < optBackUpList.size(); i++) {
//            //get status and id from file
//            String id = optBackUpList.get(i).getId();
//            String status = optBackUpList.get(i).getStatus();
//            //update status back to database
//            System.out.println("roll back " + id);
//            System.out.println("status is "+ status);
//            statusUpdateService.updateOperatorStatus(status, id);
//        }
//
//    }



}
