package eventplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB{
	protected Connection connection; 
	
	public void testConn() throws SQLException {
           	
            		String url = "jdbc:postgresql://localhost:5432/postgres";
            		String userDB ="postgres";
            		String passwordDB="12345";
            	   				
            					connection =DriverManager.getConnection(url,userDB,passwordDB);;
            		
            		System.out.print("connected to DB done successfully");
        
    }
	public Connection getConnection() 
	{
		return connection;
	}	
}
