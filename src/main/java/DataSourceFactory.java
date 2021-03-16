import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {

    public static DataSource getPostgresDataSource(String username, String password) {
        Properties props = new Properties();

        try {
            FileInputStream fis=new FileInputStream("dao.properties");
            props.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        String url = "jdbc:postgresql://ext-ody-db.ccmauy4qik1d.us-east-1.rds.amazonaws.com/postgres";

        dataSource.setURL(props.getProperty("dbUrl"));
        dataSource.setUser(username);
        dataSource.setPassword(password);

        return dataSource;

    }
}
