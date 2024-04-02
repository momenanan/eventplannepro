package eventplanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ConnectDB{
	
	protected static Connection connection; 
		 private static final Logger logger = Logger.getLogger(ConnectDB.class.getName());
	
	
		 
		 
	public static Connection getConnection() 
	{
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String userDB ="postgres";
		String password="12345";
	   				
					try {
						connection =DriverManager.getConnection(url,userDB,password);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
logger.log(Level.INFO,"connection sucssesfully");
		return connection;
	}	
}
