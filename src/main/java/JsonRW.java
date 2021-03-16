import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CognitoUser;
import model.Customer;
import model.OidBackUp;
import model.Operators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class JsonRW {

    public List<CognitoUser> readJsonFile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<CognitoUser> cognitoUserList = null;
        try {
            cognitoUserList = mapper.readValue(new File(path),
                    mapper.getTypeFactory().constructCollectionType(List.class, CognitoUser.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cognitoUserList;
    }

    public List<OidBackUp> readOidBackUpFromfile(String path) {
        ObjectMapper mapper = new ObjectMapper();
//        model.CognitoUser[] cognitoUsers = mapper.readValue(new File(path), model.CognitoUser[].class);

        List<OidBackUp> oidBackUpList = new ArrayList<>();
        try {
            oidBackUpList = mapper.readValue(new File(path),
                    mapper.getTypeFactory().constructCollectionType(List.class, OidBackUp.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oidBackUpList;

    }
    public List<Customer> readMerStatusBackUpFromfile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> merStatusBackUpList = new ArrayList<>();
        try {
            merStatusBackUpList = mapper.readValue(new File(path),
                    mapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return merStatusBackUpList;
    }
    public List<Operators> readOptStatusBackUpFromfile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        List<Operators> optStatusBackUpList = new ArrayList<>();
        try {
            optStatusBackUpList = mapper.readValue(new File(path),
                    mapper.getTypeFactory().constructCollectionType(List.class, Operators.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return optStatusBackUpList;
    }

    public void writeOidBackUpJsonFile(List<OidBackUp> list) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("OidBackUp.json").toFile(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMerchantStatusBackUpJson(List<Customer> list) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("MerchantStatusBackUp.json").toFile(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void writeOperatorStatusBackUpJson(List<Operators> list) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("OperatorStatusBackUp.json").toFile(), list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
