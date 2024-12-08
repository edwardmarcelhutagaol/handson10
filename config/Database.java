package config;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private String userName;
    private String password;
    private String host;
    private String port;
    private String dbName;
    private Connection connection;

    public Database(final String userName,final String password,final String host,final String port,final String dbName) {
        this.userName = userName;
        this.password = password;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }
    public void setup(){
        String mysqlConnUrlTemplate = "jdbc:mysql://%s:%s/%s";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(String.format(mysqlConnUrlTemplate, host, port, dbName), userName, password);
            System.out.println("Database connection is successful !");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public Connection getConnection() {
        return connection;
    }
}
