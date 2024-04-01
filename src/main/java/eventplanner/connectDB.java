package eventplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
    private static ConnectDB instance;
    private Connection connection;

    private ConnectDB() {
        // Private constructor to prevent instantiation outside of this class
    }

    public static ConnectDB getInstance() {
        if (instance == null) {
            instance = new ConnectDB();
        }
        return instance;
    }

    public void connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "12345";
        try {
            connection = DriverManager.getConnection(url, user, Password);
            logger.log(Level.INFO,"Connected to the database successfully.")
        } catch (SQLException e) {
            logger.log(Level.INFO,"Error connecting to the database")
            throw e; // Propagate the exception to the caller for handling
        }
    }

    public Connection getConnection() {
        return connection;
    }

   
    
}
