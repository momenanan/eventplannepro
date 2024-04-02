package eventplanner;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;


public class UserLoginPage {
    private String whoLastLogin;
    private boolean userLoginPass;
	
protected boolean adminIsLogin; 
protected boolean userIsLogin; 
protected boolean surviseIsLogin;
protected boolean loginFlag; 
	
    
    private String typeAdmin = "admin";
    private String typeUser = "user";
    private String typeSupervisor = "supervisor";
    
    private String userEmail;
    private String userPassword;
    private int userIDFromDB;

 	 private static final Logger logger = Logger.getLogger(UserLoginPage.class.getName());
       

    public int getUserId() {
        return userIDFromDB;
    }
	public UserLoginPage() // initilazation for this calss
	{
		adminIsLogin=false;
		userIsLogin=false;
		surviseIsLogin=false;
		
		loginFlag=false;
		
		userEmail=null;
		userPassword=null;
	}
	
	public UserLoginPage(String userEmail, String userPassword) { 
		this.userEmail=userEmail;
		this.userPassword=userPassword;
	}
	
	public boolean isValidCredentials(String userEmail, String userPassword,String typeOf) throws SQLException {
		 ConnectDB conDB = new ConnectDB();
		 
		 String sql = "SELECT * FROM "+typeOf+"";
		
		 try {
    	       Statement stmt = conDB.getConnection().createStatement();
				 
				 
	        ResultSet resultSet = stmt.executeQuery(sql);
			
	        while(resultSet.next()) {
	        	
	        String s1=resultSet.getString(6);
	        String s2=resultSet.getString(7);
		   
	        if(s1.equals(userEmail)&&s2.equals(userPassword)) {
			
	 logger.info("s1: " + s1 +"\n");
        logger.info("s2: " + s2 +"\n");
	 		     
		    	
					this.adminIsLogin=false;
					this.userIsLogin=false;
					this.surviseIsLogin=false;
					this.loginFlag=false;
			
			
			
					if(typeOf.equals(typeAdmin))
					{
						
						this.adminIsLogin=true;
						this.loginFlag=true;
						whoLastLogin=typeAdmin;
					return true;
					}
					else if(typeOf.equals(typeUser)) 
					{
						userIDFromDB=resultSet.getInt(1);
						this.userIsLogin=true;
						userLoginPass=this.loginFlag=true;
						whoLastLogin=typeUser;
					return true;
					}  
					else 
					{
						this.surviseIsLogin=true;
						this.loginFlag=true;
						whoLastLogin=typeSupervisor;
				return true;
					}
								
	     }		
	        
	        }
	  return false;
		}
		catch(SQLException e)
		{
           logger.log(Level.INFO,"e");
		}
		return false;
		
		
		
		/////////////////////////////
		
			
	}

	
	
	
	
	public boolean isUserLogged() 
	{
		return this.loginFlag;
	}
	
	public boolean logOut() 
	{
		this.loginFlag=false;
		return this.loginFlag;
	}
	
	public boolean isAdminLogged()
	{
	  return this.adminIsLogin;	
	}
	
	public boolean isUserLogged()
	{
		return this.userIsLogin;
	}
	
	public boolean isSurviseLogged()
	{
		return this.surviseIsLogin;
	}
	
	public boolean adminLogout()
	{
		this.adminIsLogin=false;
		return this.adminIsLogin;
	}
	
	public boolean userLogout()
	{
		this.userIsLogin=false;
		return this.userIsLogin;
	}
	
	public boolean surviseLogout()
	{
		this.surviseIsLogin=false;
		return this.surviseIsLogin;
	}
    public String getUserEmail() {
        return this.userEmail;
    }

    
    public String getWhoLogIn(){
    return whoLastLogin;	
    	
    }
 
    public boolean getIfuserLogIn(){
    	return userLoginPass;
    }
}
