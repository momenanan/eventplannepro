package eventplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectDB{
	protected Connection connection; 
		 private static final Logger logger = Logger.getLogger(ConnectDB.class.getName());

	
	public void testConn() throws SQLException {
           	
            		String url = "jdbc:postgresql://localhost:5432/postgres";
            		String userDB ="postgres";
            		String password="12345";
            	   				
            					connection =DriverManager.getConnection(url,userDB,password);;
            		
 logger.log(Level.INFO,"connection sussesfully");
    }
	public Connection getConnection() 
	{
		return connection;
	}	
}
