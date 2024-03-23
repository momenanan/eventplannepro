package eventplanner;


import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Application {

private user u;

/////////////////////////////////////
private boolean flag;

private String WhoLogIn;
private boolean is_venue_av;
private boolean is_venue_cap;
private boolean is_venue_time=true;
private boolean is_overlap;
private boolean is_filter_offer;
private boolean is_offer_av;
private boolean is_choose_pass;
private boolean is_search_pass;
////////////////////////////////////



public boolean get_is_venue_av(){
return is_venue_av;		
}

public boolean get_is_venue_cap() {
return is_venue_cap;	
}

public void SetWhoLogIn(String t){	
	WhoLogIn=t;
}

public boolean getWhoLogIn(){
	return WhoLogIn.equals("users");	
}

public boolean get_is_venue_time(){
	return is_venue_time;	
}

public boolean is_book_venue_pass(boolean av1,boolean t1,boolean cap1){		
		return av1&&t1&&cap1; 
}

public boolean Does_venue_av(int v_number){

	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
	  
	  String st_venue = "select * from venue";
		try {
			Connection con_venue=DriverManager.getConnection(url,userDB,passwordDB);;	 
			Statement statement_venue=con_venue.createStatement();				
			ResultSet rs_venue = statement_venue.executeQuery(st_venue);
			
			while(rs_venue.next()){
				if(rs_venue.getString(1).equals(Integer.toString(v_number)))
				{					
       
					if(is_venue_av=rs_venue.getBoolean(2)){     
             
					return true;
					}		
					
				}
				
			}
//JOptionPane.showMessageDialog(null,"sorry we could not find this venue");
			return false;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      return false;
		}

	
}

public boolean time_realistic(int start_at,int end_at){
	
	return start_at<end_at;
	
}
public boolean same_venue(int x_fromDB,int v_fromUser){

	return x_fromDB == v_fromUser;	
}
public boolean not_valid_time(int start_at,int end_at,int cStart,int cEnd){	
	//return false;
return((end_at<=cEnd)&&(cStart<end_at))||(cStart<=start_at)&&(cEnd>start_at)||(cEnd<=end_at)&&(cStart>=start_at);
}

public boolean Does_venue_time(int v_number,String d,int start_at,int end_at){
	
	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_event = "select * from event";
 
    String st_calender = "select * from calender";
			  
	  
	  
	  
	  try {
			Connection con_event=DriverManager.getConnection(url,userDB,passwordDB);;	 
			Statement statement_event=con_event.createStatement();				
			ResultSet rs_event = statement_event.executeQuery(st_event);
		
			Connection con_calender=DriverManager.getConnection(url,userDB,passwordDB);;	 
			Statement statement_calender=con_calender.createStatement();				
			ResultSet rs_calender = statement_calender.executeQuery(st_calender);
		
			
			
			while(rs_event.next()) {
				
				int x1=rs_event.getInt(8);
				int xEventIx=rs_event.getInt(1);
				
				if(same_venue(x1,v_number)) {
					while(rs_calender.next()){
	//					JOptionPane.showMessageDialog(null,"this is start while rs_cal");
					    	
									if(rs_calender.getInt(5)==xEventIx){
								String sDateCalender=rs_calender.getString(2);	
							
								if(time_realistic(start_at,end_at)&&(sDateCalender.equals(d))){
									
									
									String sStartCalender=rs_calender.getString(3);	
									String sEndCalender=rs_calender.getString(4);	
									
									String s31=sStartCalender.substring(0,2);
									    int cStart = Integer.parseInt(s31);
									String s41=sEndCalender.substring(0,2);
								    	int cEnd=Integer.parseInt(s41);
							
								    	
										//IntRang interval = new IntRang(cStart,cEnd);
									
									if(not_valid_time(start_at,end_at,cStart,cEnd)){
										is_venue_time=false;
										return false;
	                           	}
								
								}
								}
								}
		}
				}
	  
			return false;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      return false;
		}

	
}

public boolean Does_venue_capasity(int v_number,int guist_number){
	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_venue = "select * from venue";
 
   
			try {
				
				Connection con_venue=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statement_venue=con_venue.createStatement();					
				ResultSet rs_venue = statement_venue.executeQuery(st_venue);
				
				
				while(rs_venue.next()){
					
				if(rs_venue.getInt(1) == v_number){
		//		JOptionPane.showMessageDialog(null,rs_venue.getInt(3)+"   "+guist_number);	
					if(rs_venue.getInt(3)>guist_number){
						
						is_venue_cap = true;
						return true;
					}
					else return false;
					
				}	
				
				
      				}
				return false;			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
				
			}
	
}



/////////////////////////////////////////////////////////////////////

public void get_List_offer()
{

	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_offer = "select * from offer";
 
    
   			try {
				
				Connection con_offer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statement_offer=con_offer.createStatement();					
				ResultSet rs_offer = statement_offer.executeQuery(st_offer);
				
		    
				while(rs_offer.next()){
			
			int o_id = rs_offer.getInt(1);		
			String ser_Type = rs_offer.getString(2);	
	        String serP_name = rs_offer.getString(3);
	        boolean ser_Av = rs_offer.getBoolean(4);
	        int sp_fk = rs_offer.getInt(5);
	        int fees_offer = rs_offer.getInt(6);
	   
	        System.out.printf("offer_id: "+o_id+"    ser_Type : "+ser_Type+"    serP_name : "+serP_name+"    ser_Av :"+ser_Av+"    sp_fk: "+sp_fk+"    fees: "+fees_offer+"\n");
	        
      				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
    
}

//////////////////////////////////////////////////////////////////////////
public boolean is_min_max(int min_price,int max_price,int fees){
	
	return min_price<=fees&& fees<=max_price;
	
}


public boolean return_is_filter_offer() 
{
return is_filter_offer;
}


public int number_column_after_filter(int min_price,int max_price) {
	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_offer = "select count(feesofoffer) from offer where feesofoffer between "+min_price+"and "+max_price;
 
    
	
	Connection con_offer;
	try {
		con_offer = DriverManager.getConnection(url,userDB,passwordDB);
		Statement statement_offer=con_offer.createStatement();					
		int rs_offer = statement_offer.executeUpdate(st_offer);

   return rs_offer;
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}	 
		
}
public ArrayList<Integer> filter_price_offer(int min_price,int max_price){
	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_offer = "select * from offer";
 
	 ArrayList <Integer> a = new ArrayList<Integer>();	
			try {
				
				Connection con_offer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statement_offer=con_offer.createStatement();					
				ResultSet rs_offer = statement_offer.executeQuery(st_offer);
				int fees_offer;
					
				while(rs_offer.next())
				 {
			         fees_offer = rs_offer.getInt(6);	

			         if(is_min_max(min_price,max_price,fees_offer)) {	
	
			int o_id = rs_offer.getInt(1);		
			
			String ser_Type = rs_offer.getString(2);	
	        String serP_name = rs_offer.getString(3);
	        boolean ser_Av = rs_offer.getBoolean(4);
	        int sp_fk = rs_offer.getInt(5);
	        is_filter_offer = true;
			a.add(fees_offer);
			
	        System.out.printf("offer_id : "+o_id+"    ser_Type : "+ser_Type+"    serP_name : "+serP_name+"    ser_Av : "+ser_Av+"    sp_fk : "+sp_fk+"    fees: "+fees_offer+"\n");
  	} 
			         
      				}
				return a;		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return a;
			}   		
}

public boolean return_offer_av(){
	return is_offer_av;
}

public boolean get_is_choose_pass(){	
return is_choose_pass;	
}
public boolean choose_offer(int o_number){
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_offer = "select * from offer";
 
    
			try {
				
				Connection con_offer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statement_offer=con_offer.createStatement();					
				ResultSet rs_offer = statement_offer.executeQuery(st_offer);
				while(rs_offer.next()){

					if(rs_offer.getInt(1)==o_number){
					//	JOptionPane.showMessageDialog(null,rs_offer.getInt(1)+"---"+o_number+"---"+rs_offer.getBoolean(4));
	            		return is_choose_pass=rs_offer.getBoolean(4);
 					
					}
				}
				return false;
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}   		
	
}


//////////////////////////////////////Vendor

public boolean get_is_search_pass() 
{
return is_search_pass;
}
public boolean get_search_vendor(int n_vendor){
	
	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_offer = "select * from offer";
 
    
			try {
				
				Connection con_offer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statement_offer=con_offer.createStatement();					
				ResultSet rs_offer = statement_offer.executeQuery(st_offer);
				while(rs_offer.next()){

					if(rs_offer.getInt(5) == n_vendor){
					//JOptionPane.showMessageDialog(null,rs_offer.getInt(1)+"---"+o_number+"---"+rs_offer.getBoolean(4));
	            	
						return is_search_pass=rs_offer.getBoolean(4);					
					}
				}
				
				return false;
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
}

}
