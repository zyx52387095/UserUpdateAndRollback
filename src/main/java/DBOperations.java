import javax.sql.DataSource;
import java.sql.*;


public class DBOperations {
    static Connection conn;

    public DBOperations(String dbUserName, String dbPassword) {
        conn=getConnection(dbUserName, dbPassword);
    }

    public Connection getConnection(String userName, String passWord) {
        Connection conn = null;
        DataSource dataSource = DataSourceFactory.getPostgresDataSource(userName,passWord);
        try {
            conn = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }


//    static DataSource dataSource = DataSourceFactory.getPostgresDataSource("postgres", "897t4sgx8at");


    //get all merchant users without status: deleted, suspended and email_sent
    static String sqlSelectAllMerchants = "select id, customer_status "
            + "from customer "
            + "where customer_status!='DELETED' "
            + "and customer_status!='ACQ_SUSPENDED' "
            + "and customer_status!='EMAIL_SENT'";
    //get all operator users without status: deleted and email_sent
    static String sqlSelectAllOperators = "select id, status "
            + "FROM fsdp_user "
            + "WHERE status != 'DELETED'"
            + "and status!='EMAIL_SENT'";
    //get all suspended users
    static String sqlSelectAllSuspended = "select id, customer_status "
            + "from customer "
            + "where customer_status = 'ACQ_SUSPENDED'";


    static String sqlUpdateOperatorStatus = "update fsdp_user "
            + "set status = ? "
            + "where id = ?";
    static String sqlUpdateaMerchantStatus = "update customer "
            + "set customer_status = ? "
            + "where id = ?";
    //insert into tracking table
    static String sqlInsertStatusTacking = "INSERT INTO "
            + "status_tracking(id,object_id,object_type, previous_status, current_status,update_date) "
            + "VALUES(?,?,?,?,?,?)";;


    static String sqlGetMerchantId = "select e.EMAIL,chc.CUSTOMER_ID as id "
            + "from customer_has_contact chc "
            + "join email e on e.contact_id = chc.contact_id "
            + "where EMAIL = ?";
    static String sqlGetOperatorId = "select fuser.ID" +
            " from principal p" +
            " join credential c on c.principal_id = p.id" +
            " join password_credential pass on pass.id = c.id" +
            " join fsdp_user fuser on fuser.id = p.id" +
            " where pass.login_name = ?";
    //update CognitoID
    static String sqlUpdateCognitoID = "update security_principal "
            + "set oid = ? "
            + "where pr_identity = ?";

    //get original oid by pr_identity
    static String getOIdandPrIdentity = "SELECT pr_identity, oid "
            + "FROM security_principal "
            + "WHERE pr_identity = ?";





}
