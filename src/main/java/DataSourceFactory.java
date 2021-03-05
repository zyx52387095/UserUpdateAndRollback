import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {

    public static DataSource getPostgresDataSource(String username, String password) {
        Properties props = new Properties();
        FileInputStream fis = null;

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String url = "jdbc:postgresql://ext-ody-db.ccmauy4qik1d.us-east-1.rds.amazonaws.com/postgres";

        dataSource.setURL(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        return dataSource;

    }
}
