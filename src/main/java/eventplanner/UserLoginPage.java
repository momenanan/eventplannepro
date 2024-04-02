package eventplanner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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
		 
		 String sql = "SELECT * FROM "+type_of+"";
		
		 try {
    	       Statement stmt = conDB.getConnection().createStatement();
				 
				 
	        ResultSet resultSet = stmt.executeQuery(sql);
			
	        while(resultSet.next()) {
	        	
	        String s1=resultSet.getString(6);
	        String s2=resultSet.getString(7);
		   
	        if(s1.equals(user_email)&&s2.equals(user_password)) {
	        	 System.out.printf("s1: "+s1+"\n");
	 		     System.out.printf("s2: "+s2+"\n");
	 		     
		    	
					this.Admin_is_login=false;
					this.user_is_login=false;
					this.sutvise_is_login=false;
					this.login_flag=false;
			
			
			
					if(type_of.equals(type_admin))
					{
						
						this.Admin_is_login=true;
						this.login_flag=true;
						WhoLastLogIn=type_admin;
					return true;
					}
					else if(type_of.equals(type_user)) 
					{
						UserIDFromDB=resultSet.getInt(1);
						this.user_is_login=true;
						UserLogInPass=this.login_flag=true;
						WhoLastLogIn=type_user;
					return true;
					}  
					else 
					{
						this.sutvise_is_login=true;
						this.login_flag=true;
						WhoLastLogIn=type_sutvise;
				return true;
					}
								
	     }		
	        
	        }
	  return false;
		}
		catch(SQLException e)
		{
			System.out.print(e);
		}
		return false;
		
		
		
		/////////////////////////////
		
			
	}

	
	
	
	
	public boolean is_user_logged() 
	{
		return this.login_flag;
	}
	
	public boolean Logout() 
	{
		this.login_flag=false;
		return this.login_flag;
	}
	
	public boolean is_Admin_logged()
	{
	  return this.Admin_is_login;	
	}
	
	public boolean is_User_logged()
	{
		return this.user_is_login;
	}
	
	public boolean is_survise_logged()
	{
		return this.sutvise_is_login;
	}
	
	public boolean Admin_logout()
	{
		this.Admin_is_login=false;
		return this.Admin_is_login;
	}
	
	public boolean user_logout()
	{
		this.user_is_login=false;
		return this.user_is_login;
	}
	
	public boolean survise_logout()
	{
		this.sutvise_is_login=false;
		return this.sutvise_is_login;
	}
    public String getUser_email() {
        return this.user_email;
    }

    
    public String getWhoLogIn(){
    return WhoLastLogIn;	
    	
    }
 
    public boolean getIfUserLogIn(){
    	return UserLogInPass;
    }
}
