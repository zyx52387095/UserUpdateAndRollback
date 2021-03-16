import model.CognitoUser;
import model.OidBackUp;


import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CognitoIdMain {
    private static final Logger LOGGER = Logger.getLogger( CognitoIdMain.class.getName() );
    public static void main(String[] args){

        if (args.length==0) {
            System.out.println("arg path is null or empty");
            System.exit(0);
        }
        String merOrOpt = args[0];
        String path = args[1];

        Console console = System.console();
        console.printf("Please enter your username: ");
        String username = console.readLine();
        console.printf(username + "\n");
        console.printf("Please enter your password: ");
        char[] passwordChars = console.readPassword();
        String passwordString = new String(passwordChars);
        if ("mer".equals(merOrOpt.trim())) {
            updateCognitoIdForMerchants(username, passwordString, path);
        } else if ("opt".equals(merOrOpt.trim())) {
            updateCognitoIdForOperators(username, passwordString, path);
        } else {
            System.out.println("wrong arguments");
            LOGGER.info("Wrong arguments");
        }
    }

    /**
     * Update all merchant users's oid with Cognito id
     * @param userName database username
     * @param passWord database password
     * @param path path of the Cognito json file
     */

    public static void updateCognitoIdForMerchants(String userName, String passWord, String path) {
        LOGGER.info("Updating Cognito id for Merchant users");
        JsonRW jsonRW = new JsonRW();
        CognitoIdService cognitoIdService = new CognitoIdService(userName, passWord);
        List<OidBackUp> oidBackUpList = new ArrayList<>();
        List<CognitoUser> cognitoUserList = jsonRW.readJsonFile(path);
        LOGGER.info("There are "+cognitoUserList.size()+ " users to be updated");

        for (int i = 0; i < cognitoUserList.size(); i++) {
            String email = cognitoUserList.get(i).email;
            String cognitoId = cognitoUserList.get(i).username;
            String merchantId = cognitoIdService.getMerchantIdByUserName(email);
            //get rid of the extra spaces at the end of string
            LOGGER.info("Got user id: "+ merchantId);
            String newId = String.format("%-36s", merchantId);;
            LOGGER.info("adding extra length: "+ newId);

            //first save the old record
            OidBackUp oidBackUp= cognitoIdService.getBackUpJsonForOId(newId);
            if(oidBackUp!=null) {
                System.out.println("backup oid is " + oidBackUp.getoId());
                System.out.println("backup identity is " + oidBackUp.getPr_identity());
                LOGGER.info("backup oid is " + oidBackUp.getoId());
                LOGGER.info("backup identity is " + oidBackUp.getoId());
                oidBackUpList.add(oidBackUp);
                cognitoIdService.updateCognitoID(cognitoId, newId);
            }
        }
        jsonRW.writeOidBackUpJsonFile(oidBackUpList);

    }


    /**
     * Update all operator users's oid with Cognito id
     * @param userName database username
     * @param psssWord database password
     * @param path path of the Cognito json file
     */
    public static void updateCognitoIdForOperators(String userName, String psssWord, String path) {
        LOGGER.info("Updating Cognito id for Operator users");
        JsonRW jsonRW = new JsonRW();
        CognitoIdService cognitoIdService = new CognitoIdService(userName, psssWord);
        List<OidBackUp> oidBackUpList = new ArrayList<>();
        List<CognitoUser> cognitoUserList = jsonRW.readJsonFile(path);
        LOGGER.info("There are "+cognitoUserList.size()+ " users to be updated");

        for (int i = 0; i < cognitoUserList.size(); i++) {
            String email = cognitoUserList.get(i).email;
            String cognitoId = cognitoUserList.get(i).username;
            String optId = cognitoIdService.getOperatorIdByUserName(email);

            //get rid of the extra spaces at the end of string
            LOGGER.info("Got user id: "+ optId);
            String newId = String.format("%-36s", optId);
            LOGGER.info("adding extra length: "+ newId);

            //first save the old record
            OidBackUp oidBackUp= cognitoIdService.getBackUpJsonForOId(newId);
            if(oidBackUp!=null) {
                System.out.println("backup oid is " + oidBackUp.getoId());
                System.out.println("backup identity is " + oidBackUp.getPr_identity());
                LOGGER.info("backup oid is " + oidBackUp.getoId());
                LOGGER.info("backup identity is " + oidBackUp.getoId());
                oidBackUpList.add(oidBackUp);
                cognitoIdService.updateCognitoID(cognitoId,newId);
            }
        }
        jsonRW.writeOidBackUpJsonFile(oidBackUpList);

    }

}
