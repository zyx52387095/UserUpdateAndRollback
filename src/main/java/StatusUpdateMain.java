import model.Customer;
import model.Operators;
import java.io.Console;
import java.util.List;
import java.util.logging.Logger;

public class StatusUpdateMain {
    private static final Logger LOGGER = Logger.getLogger( StatusUpdateMain.class.getName());
    static StatusUpdateService statusUpdateService;
    public static void main(String[] args){
        Console console = System.console();
        console.printf("Please enter your username: ");
        String username = console.readLine();
        console.printf(username + "\n");
        console.printf("Please enter your password: ");
        char[] passwordChars = console.readPassword();
        String passwordString = new String(passwordChars);

        statusUpdateService = new StatusUpdateService(username, passwordString);

        updateOperatorStatus(username, passwordString);
        updateMerchatStatus(username, passwordString);
        updateStatusTrackingForSuspended(username, passwordString);

    }

    /**
     * Update Merchant user status to 'EMAIL_SENT'
     * @param userName database username
     * @param password database password
     */

    public static void updateMerchatStatus(String userName, String password) {
//        StatusUpdateService statusUpdateService = new StatusUpdateService(userName, password);
        JsonRW jsonRW = new JsonRW();

        List<Customer> customers = statusUpdateService.getyAllMerchantUsers();
        //create merchant backup json file
        LOGGER.info("Writing all merchant user to backup json file");
        jsonRW.writeMerchantStatusBackUpJson(customers);

        for (int i = 0; i < customers.size(); i++) {
            String status = "EMAIL_SENT";
            String id = customers.get(i).getId();
            LOGGER.info("updating user status for merchant: "+id);
            statusUpdateService.updateMerchantStatus(status, id);
        }
    }

    /**
     * Update Operator user status to 'EMAIL_SENT'
     * @param userName database username
     * @param password database password
     */
    public static void updateOperatorStatus(String userName, String password) {
//        StatusUpdateService statusUpdateService = new StatusUpdateService(userName, password);
        JsonRW jsonRW = new JsonRW();

        List<Operators> operators = statusUpdateService.getAllOperatorUsers();
        //create merchant backup json file
        LOGGER.info("Writing all operator user to backup json file");
        jsonRW.writeOperatorStatusBackUpJson(operators);

        for (int i = 0; i < operators.size(); i++) {

            String status = "EMAIL_SENT";
            String id = operators.get(i).getId();
            LOGGER.info("updating user status for operator: "+id);
            statusUpdateService.updateOperatorStatus(status, id);
        }
    }

    /**
     * Insert into status_tracking table for suspended users
     * @param username database username
     * @param password database password
     */
    public static void updateStatusTrackingForSuspended(String username, String password) {
//        StatusUpdateService statusUpdateService = new StatusUpdateService(username, password);
        List<Customer> suspendedUsers = statusUpdateService.getAllSuspendedUsers();
        for (int i = 0; i < suspendedUsers.size(); i++) {
            String Id = suspendedUsers.get(i).getId();
            String curStatus = suspendedUsers.get(i).getStatus();
            System.out.println("current status is "+ curStatus);
            LOGGER.info("inserting suspended user to status_tracking table "+Id);
            statusUpdateService.addStatusTrackingRecord(Id, curStatus,
                    "EMAIL_SENT", "CUSTOMER");
        }
    }

}
