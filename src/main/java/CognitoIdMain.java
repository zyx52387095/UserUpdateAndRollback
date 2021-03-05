import model.CognitoUser;
import model.OidBackUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CognitoIdMain {
    public static void main(String[] args) throws IOException {
        String path = ".\\CognitoIds.json";

//        if (args.length==0) {
//
//            System.out.println("arg path is null or empty");
//            System.exit(0);
//        }
//
//        String path = args[0];

        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("username?");
        String dbUsername = c.readLine();
        System.out.println("password?");
        String dbPassword = c.readLine();

        updateCognitoIdForOperators(dbUsername, dbPassword, path);



    }

    public static void updateCognitoIdForMerchants(String userName, String passWord, String path) {
        JsonRW jsonRW = new JsonRW();
        CognitoIdService cognitoIdService = new CognitoIdService(userName, passWord);
        List<OidBackUp> oidBackUpList = new ArrayList<>();
        List<CognitoUser> cognitoUserList = jsonRW.readJsonFile(path);

        System.out.println("update size is "+cognitoUserList.size());
        for (int i = 0; i < cognitoUserList.size(); i++) {
            String email = cognitoUserList.get(i).email;
            String cognitoId = cognitoUserList.get(i).username;
            String merchantId = cognitoIdService.getMerchantIdByUserName(email);

            //get rid of the extra spaces at the end of string
            System.out.println("user id is "+ merchantId);
            String newOId = String.format("%-36s", merchantId);;

            //first save the old record
            OidBackUp oidBackUp= cognitoIdService.getBackUpJsonForOId(newOId);
            System.out.println("backup oid is "+oidBackUp.getoId());
            System.out.println("backup identity is "+oidBackUp.getPr_identity());
            oidBackUpList.add(oidBackUp);

            cognitoIdService.updateCognitoID(cognitoId,newOId);
        }
        System.out.println("backup size is : "+oidBackUpList.size());
        jsonRW.writeOidBackUpJsonFile(oidBackUpList);

    }


    public static void updateCognitoIdForOperators(String userName, String psssWord, String path) {
        JsonRW jsonRW = new JsonRW();
        CognitoIdService cognitoIdService = new CognitoIdService(userName, psssWord);
        List<OidBackUp> oidBackUpList = new ArrayList<>();
        List<CognitoUser> cognitoUserList = jsonRW.readJsonFile(path);

        System.out.println("update size is "+cognitoUserList.size());
        for (int i = 0; i < cognitoUserList.size(); i++) {
            String email = cognitoUserList.get(i).email;
            String cognitoId = cognitoUserList.get(i).username;
            String optId = cognitoIdService.getOperatorIdByUserName(email);

            //get rid of the extra spaces at the end of string
            System.out.println("user id is "+ optId);
            String newOId = String.format("%-36s", optId);;

            //first save the old record
            OidBackUp oidBackUp= cognitoIdService.getBackUpJsonForOId(newOId);
            System.out.println("backup oid is "+oidBackUp.getoId());
            System.out.println("backup identity is "+oidBackUp.getPr_identity());
            oidBackUpList.add(oidBackUp);

            cognitoIdService.updateCognitoID(cognitoId,newOId);
        }
        System.out.println("backup size is : "+oidBackUpList.size());
        jsonRW.writeOidBackUpJsonFile(oidBackUpList);

    }

}
