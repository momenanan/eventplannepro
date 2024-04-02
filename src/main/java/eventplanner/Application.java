package eventplanner;


import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Application {

private User u;

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
private boolean is_filter_venue;
////////////////////////////////////
private String Location_venue_cla;
private int  fees_venue_cla;
private int SP_fk_cla;
private int event_fk_after_creation;


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
						
               Location_venue_cla=rs_venue.getString(5);
			   fees_venue_cla=rs_venue.getInt(6);
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

						String sDateCalender=rs_calender.getString(2);	
									
						System.out.printf("  vN: "+v_number);
						System.out.printf("    d: "+d+"  and in cal : "+sDateCalender);
						System.out.printf("     start_at: "+start_at);
						System.out.printf("       end_at: "+end_at);
			
						if(rs_calender.getInt(5)==xEventIx){
			
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
								else return true;
								}
								}return true;
		}
				}
	  
			return true;
	
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
/////////////////////////////////////////////////


public ArrayList<Integer> filter_price_venue(int min_price,int max_price){
	
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String userDB ="postgres";
	String passwordDB="12345";
    String st_venue = "select * from venue";
 
    
	 ArrayList <Integer> a = new ArrayList<Integer>();	
			try {
				
				Connection con_venue=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statement_venue=con_venue.createStatement();					
				ResultSet rs_venue = statement_venue.executeQuery(st_venue);
				int fees_venue;
					
				while(rs_venue.next())
				 {
			         fees_venue = rs_venue.getInt(6);	

			         if(is_min_max(min_price,max_price,fees_venue)) {	
	
			int v_id = rs_venue.getInt(1);		
			
			boolean v_av = rs_venue.getBoolean(2);	
	        String cap = rs_venue.getString(3);
	        String amen = rs_venue.getString(4);
	        String location =rs_venue.getString(5);
	        int fees = rs_venue.getInt(6);
	        is_filter_venue = true;
			a.add(fees_venue);
			
	        System.out.printf("venue_id : "+v_id+"    v_avai : "+v_av+"    cap : "+cap+"    amenities : "+amen+"    Location : "+location+"    fees: "+fees+"\n");
  	} 
			         
      				}
				return a;		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return a;
			}   		
}
















//////////////////////////////////////////////////
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

					if((rs_offer.getInt(1)==o_number)){
					//	JOptionPane.showMessageDialog(null,rs_offer.getInt(1)+"---"+o_number+"---"+rs_offer.getBoolean(4));
	            	SP_fk_cla=rs_offer.getInt(5);
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
					//	SP_fk_cla =rs_offer.getInt(5);
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

////////////////////////////////////////////////////

public boolean Go_book(int vID,int cap,String d,int startHourFromUser,int endHourFromUser,String amen,int uID) 
{
boolean ret;
System.out.printf("1-"+Does_venue_capasity(vID,cap)+" \n");
System.out.printf("2-"+Does_venue_time(vID,d,startHourFromUser,endHourFromUser)+"\n");

if(ret= Does_venue_capasity(vID,cap)&&Does_venue_time(vID,d,startHourFromUser,endHourFromUser)&&Does_venue_av(vID)){

	   String url = "jdbc:postgresql://localhost:5432/postgres";
	   String userDB ="postgres";
	   String passwordDB="12345";
	   
	  System.out.printf("Choose one of Them as offer you want: \n");	   
	  get_List_offer();
	  Scanner s = new Scanner(System.in); 
	   int o_id=s.nextInt();
	   boolean choose_pass=choose_offer(o_id);
	
	   if(!choose_pass) {
		   System.out.printf("Sorry this offer not availabe \n");
		   return false;
		   }
	  
       String st_offer = "insert into event values (Default"+",'noPer','"+d+"',"+"'"+Location_venue_cla+"',"+cap+",'"+amen+"',"+uID+","+vID+","+SP_fk_cla+")";
       
       //  JOptionPane.showMessageDialog(null,st_offer);
    //amen proplem
    			Connection con_event;
    			try {
					con_event = DriverManager.getConnection(url,userDB,passwordDB);	
					Statement statement_offer = con_event.createStatement();					
			
				
					int x = statement_offer.executeUpdate(st_offer);
					String get_index_last_row="select * from event";
					Statement St_getRow = con_event.createStatement();
					ResultSet rsRow=St_getRow.executeQuery(get_index_last_row);
					rsRow.next();
					int indexEventRow = rsRow.getInt(1);
					setCalender(d,startHourFromUser,endHourFromUser,true,indexEventRow);
							
					 
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};	 
		  
     return true;         	
}
else { 
if(!get_is_venue_av()){
	System.out.printf("Sorry Not available \n");
}	
if(!get_is_venue_cap()){
	
System.out.printf("Sorry Tha Capasity not enough \n");	
}

if(!get_is_venue_time()){
	System.out.printf("Sorry The Time Overlap \n");	
}
}

return ret;

}
//////////////////////////////////
public boolean setCalender(String D,int s,int e,boolean is_a,int indexEventRow) 
{

	LocalTime s2 = LocalTime.of(s,0);
	
	LocalTime e2 = s2.plusHours(e-s);
	
	   String url = "jdbc:postgresql://localhost:5432/postgres";
	   String userDB ="postgres";
	   String passwordDB="12345";
	
	   Connection con_calender;
	  		
	try {
		
		
		 String sqlInsertToCalender = "insert into calender values("+"default,"+"'"+D+"',"+"'"+s2+"'"+","+"'"+e2+"'"+","+indexEventRow+","+is_a+")";
	     System.out.printf(sqlInsertToCalender);
	
		 JOptionPane.showMessageDialog(null,sqlInsertToCalender);
		con_calender = DriverManager.getConnection(url,userDB,passwordDB);
		Statement statement_calender = con_calender.createStatement();					
		 int x = statement_calender.executeUpdate(sqlInsertToCalender); 
			
		 return true;
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		return false;
	}	
	
 
	
	
}








}
