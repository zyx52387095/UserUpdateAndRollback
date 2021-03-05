import model.Customer;
import model.Operators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StatusUpdateMain {
    public static void main(String[] args) throws IOException {
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("username?");
        String dbUsername = c.readLine();
        System.out.println("password?");
        String dbPassword = c.readLine();
        updateOperatorStatus(dbUsername, dbPassword);
        updateMerchatStatus(dbUsername, dbPassword);
        updateStatusTrackingForSuspended(dbUsername, dbPassword);

    }

    public static void updateMerchatStatus(String userName, String password) {
        StatusUpdateService statusUpdateService = new StatusUpdateService(userName, password);
        JsonRW jsonRW = new JsonRW();

        List<Customer> customers = statusUpdateService.getyAllMerchantUsers();
        //create merchant backup json file
        jsonRW.writeMerchantStatusBackUpJson(customers);

        for (int i = 0; i < customers.size(); i++) {

            //todo update to the current status, will change to "email sent" after
//            String status = customers.get(i).getStatus();
            String status = "EMAIL_SENT";
            String id = customers.get(i).getId();
            int j = statusUpdateService.updateMerchantStatus(status, id);

        }

    }
    public static void updateOperatorStatus(String userName, String password) {
        StatusUpdateService statusUpdateService = new StatusUpdateService(userName, password);
        JsonRW jsonRW = new JsonRW();

        List<Operators> operators = statusUpdateService.getAllOperatorUsers();
        //create merchant backup json file
        jsonRW.writeOperatorStatusBackUpJson(operators);

        for (int i = 0; i < operators.size(); i++) {

            //todo update to the current status, will change to "email sent" after
//            String status = operators.get(i).getStatus();
            String status = "EMAIL_SENT";
            String id = operators.get(i).getId();
            int j = statusUpdateService.updateOperatorStatus(status, id);

        }
    }

    public static void updateStatusTrackingForSuspended(String username, String password) {
        StatusUpdateService statusUpdateService = new StatusUpdateService(username, password);
        List<Customer> suspendedUsers = statusUpdateService.getAllSuspendedUsers();
        for (int i = 0; i < suspendedUsers.size(); i++) {
            String oId = suspendedUsers.get(i).getId();
            String curStatus = suspendedUsers.get(i).getStatus();
            System.out.println("current status is "+ curStatus);
            statusUpdateService.addStatusTrackingRecord(oId, curStatus,
                    "EMAIL_SENT", "CUSTOMER");
        }

    }

}
