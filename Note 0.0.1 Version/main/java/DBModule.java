import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBModule {

    private static Connection connectionDB;

    public static void connect() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        connectionDB = DriverManager.getConnection("jdbc:sqlite:MDB");
    }

    public static void disconnect() throws SQLException
    {
        connectionDB.close();
    }

    public static Connection getConnectionDB() {
        return connectionDB;
    }
}
