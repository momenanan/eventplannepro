package eventplanner;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Application {

private User u;

/////////////////////////////////////
private boolean flag;

private String whoLogIn;
private boolean isVenueAv;
private boolean isVenueCap;
private boolean isVenueTime=true;
private boolean isOverlap;
private boolean isFilterOffer;
private boolean isOfferAv;
private boolean isChoosePass;
private boolean isSearchPass;
private boolean isFilterVenue;
////////////////////////////////////
private String locationVenueCla;
private int  feesVenueCla;
private int sPFkCla;
private int eventFkAfterCreation;
private static final Logger logger = Logger.getLogger(Application.class.getName());
private static final String DBURL = "jdbc:postgresql://localhost:5432/postgres";
private static final String POSTGRESDBNAME = "postgres";
private static final String PASSWORDDB = "12345momen";
	private static final String SELECTALLFROMVENUE = "select * from venue";
	private static final String SELECTALLFROMEVENT = "select * from event";
	private static final String SELECTALLFROMCELENDER = "select * from celender";
	private static final String SELECTALLFROMOFFER = "select * from offer";









public boolean getIsVenueAv(){
return isVenueAv;		
}

public boolean getIsVenueCap() {
return isVenueCap;	
}

public void setWhoLogIn(String t){	
	whoLogIn=t;
}

public boolean getWhoLogIn(){
	return whoLogIn.equals("users");	
}

public boolean getIsVenueTime(){
	return isVenueTime;	
}

public boolean isBookVenuePass(boolean av1,boolean t1,boolean cap1){		
		return av1&&t1&&cap1; 
}



public boolean doesVenueAv(int vNumber){

	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
	  
	  String stVenue = SELECTALLFROMVENUE;
		try(Connection conVenue=DriverManager.getConnection(url,userDB,passwordDB); ) {
			Statement statementVenue=conVenue.createStatement();				
			ResultSet rsVenue = statementVenue.executeQuery(stVenue);
			
			while(rsVenue.next()){
				if(rsVenue.getString(1).equals(Integer.toString(vNumber)))
				{					
       
					if(isVenueAv=rsVenue.getBoolean(2)){     
						
               locationVenueCla=rsVenue.getString(5);
			   feesVenueCla=rsVenue.getInt(6);
               return true;
					}		
					
				}
				
			}
			return false;
	
		} catch (SQLException e) {
			e.printStackTrace();
      return false;
		}

	
}

public boolean timeRealistic(int startAt,int endAt){
	
	return startAt<endAt;
	
}
public boolean sameVenue(int xFromDb,int vFromUser){

	return xFromDb == vFromUser;	
}
public boolean notValidTime(int startAt,int endAt,int cStart,int cEnd){	
return((endAt<=cEnd)&&(cStart<endAt))||(cStart<=startAt)&&(cEnd>startAt)||(cEnd<=endAt)&&(cStart>=startAt);
}

public boolean doesVenueTime(int vNumber,String d,int startAt,int endAt){
	
	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stEvent = SELECTALLFROMEVENT;
 
    String stCalender = SELECTALLFROMCELENDER;
			  
	  
	  
	  
	  try {
			Connection conEvent=DriverManager.getConnection(url,userDB,passwordDB);;	 
			Statement statementEvent=conEvent.createStatement();				
			ResultSet rsEvent = statementEvent.executeQuery(stEvent);
		
			Connection conCalender=DriverManager.getConnection(url,userDB,passwordDB);;	 
			Statement statementCalender=conCalender.createStatement();				
			ResultSet rsCalender = statementCalender.executeQuery(stCalender);
		
			
		
			while(rsEvent.next()) {
				
				int x1=rsEvent.getInt(8);
				int xEventIx=rsEvent.getInt(1);
				
				
				if(sameVenue(x1,vNumber)) {
				
					
					while(rsCalender.next()){

						String sDateCalender=rsCalender.getString(2);	
									
						logger.log(Level.INFO, String.format("  vN: %d", vNumber));
                                        logger.log(Level.INFO, String.format("    d: %s  and in cal : %s", d, sDateCalender));
                                       logger.log(Level.INFO, String.format("     start_at: %d", startAt));
                                          logger.log(Level.INFO, String.format("       end_at: %d", endAt));

			
						if(rsCalender.getInt(5)==xEventIx){
			
								if(timeRealistic(startAt,endAt)&&(sDateCalender.equals(d))){
									
									
									String sStartCalender=rsCalender.getString(3);	
									String sEndCalender=rsCalender.getString(4);	
									
									String s31=sStartCalender.substring(0,2);
									    int cStart = Integer.parseInt(s31);
									String s41=sEndCalender.substring(0,2);
								    	int cEnd=Integer.parseInt(s41);
							
								    	
									
									if(notValidTime(startAt,endAt,cStart,cEnd)){
										isVenueTime=false;
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
			e.printStackTrace();
      return false;
		}

	
}

public boolean doesVenueCapasity(int vNumber,int guistNumber){
	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stVenue = SELECTALLFROMVENUE;
 
   
			try {
				
				Connection conVenue=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statementVenue=conVenue.createStatement();					
				ResultSet rsVenue = statementVenue.executeQuery(stVenue);
				
				
				while(rsVenue.next()){
					
				if(rsVenue.getInt(1) == vNumber){
					if(rsVenue.getInt(3)>guistNumber){
				
						isVenueCap = true;
						
						return true;
					}
					else return false;
					
				}	
				
				
      				}
				return false;			
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
				
			}
	
}



/////////////////////////////////////////////////////////////////////

public void getListOffer()
{

	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stOffer = SELECTALLFROMOFFER;
 
    
   			try {
				
				Connection conOffer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statementOffer=conOffer.createStatement();					
				ResultSet rsOffer = statementOffer.executeQuery(stOffer);
				
		    
				while(rsOffer.next()){
			
			int oId = rsOffer.getInt(1);		
			String serType = rsOffer.getString(2);	
	        String serPName = rsOffer.getString(3);
	        boolean serAv = rsOffer.getBoolean(4);
	        int spFk = rsOffer.getInt(5);
	        int feesOffer = rsOffer.getInt(6);
	   
	        logger.log(Level.INFO,"offer_id: "+oId+"    ser_Type : "+serType+"    serP_name : "+serPName+"    ser_Av :"+serAv+"    sp_fk: "+spFk+"    fees: "+feesOffer+"\n");
	        
      				}
			} catch (SQLException e) {
				e.printStackTrace();
			}   
    
}

//////////////////////////////////////////////////////////////////////////
public boolean isMinMax(int minPrice,int maxPrice,int fees){
	
	return minPrice<=fees&& fees<=maxPrice;
	
}


public boolean returnIsFilterOffer() 
{
return isFilterOffer;
}


public int numberColumnAfterFilter(int minPrice,int maxPrice) {
	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stOffer = "select count(feesofoffer) from offer where feesofoffer between "+minPrice+"and "+maxPrice;
 
	Connection conOffer;
	try {
		conOffer = DriverManager.getConnection(url,userDB,passwordDB);
		Statement statementOffer=conOffer.createStatement();					
		int rsOffer = statementOffer.executeUpdate(stOffer);
	
		
		
   return rsOffer;
	
	} catch (SQLException e) {
		e.printStackTrace();
		return 0;
	}	 
		
}
public ArrayList<Integer> filter_price_offer(int minPrice,int maxPrice){
	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stOffer = SELECTALLFROMOFFER;
 
	 ArrayList <Integer> a = new ArrayList<Integer>();	
			try {
				
				Connection conOffer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statementOffer=conOffer.createStatement();					
				ResultSet rsOffer = statementOffer.executeQuery(stOffer);
				int feesOffer;
					
				while(rsOffer.next())
				 {
			         feesOffer = rsOffer.getInt(6);	

			         if(isMinMax(minPrice,maxPrice,feesOffer)) {	
	
			int oId = rsOffer.getInt(1);		
			
			String serType = rsOffer.getString(2);	
	        String serPName = rsOffer.getString(3);
	        boolean serAv = rsOffer.getBoolean(4);
	        int spFk = rsOffer.getInt(5);
	        isFilterOffer = true;
			a.add(feesOffer);
			
	        logger.log(Level.INFO,"offer_id : "+oId+"    ser_Type : "+serType+"    serP_name : "+serPName+"    ser_Av : "+serAv+"    sp_fk : "+spFk+"    fees: "+feesOffer+"\n");
  	} 
			         
      				}
				return a;		        
			} catch (SQLException e) {
				e.printStackTrace();
				return a;
			}   		
}
/////////////////////////////////////////////////


public ArrayList<Integer> filter_price_venue(int minPrice,int maxPrice){
	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stVenue = SELECTALLFROMVENUE;
 
    
	 ArrayList <Integer> a = new ArrayList<Integer>();	
			try {
				
				Connection conVenue=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statementVenue=conVenue.createStatement();					
				ResultSet rsVenue = statementVenue.executeQuery(stVenue);
				int feesVenue;
					
				while(rsVenue.next())
				 {
			         feesVenue = rsVenue.getInt(6);	

			         if(isMinMax(minPrice,maxPrice,feesVenue)) {	
	
			int vId = rsVenue.getInt(1);		
			
			boolean vAv = rsVenue.getBoolean(2);	
	        String cap = rsVenue.getString(3);
	        String amen = rsVenue.getString(4);
	        String location =rsVenue.getString(5);
	        int fees = rsVenue.getInt(6);
	        isFilterVenue = true;
			a.add(feesVenue);
			
	       logger.log(Level.INFO,"venue_id : "+vId+"    v_avai : "+vAv+"    cap : "+cap+"    amenities : "+amen+"    Location : "+location+"    fees: "+fees+"\n");
  	} 
			         
      				}
				return a;		        
			} catch (SQLException e) {
				e.printStackTrace();
				return a;
			}   		
}









//////////////////////////////////////////////////
public boolean returnOfferAv(){
	return isOfferAv;
}

public boolean getIsChoosePass(){	
return isChoosePass;	
}
public boolean chooseOffer(int oNumber){
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stOffer = SELECTALLFROMOFFER;
 
    
			try {
				
				Connection conOffer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statementOffer=conOffer.createStatement();					
				ResultSet rsOffer = statementOffer.executeQuery(stOffer);
				while(rsOffer.next()){

					if((rsOffer.getInt(1)==oNumber)){
	            	sPFkCla=rsOffer.getInt(5);
						return isChoosePass=rsOffer.getBoolean(4);
 					
					}
				}
				return false;
				} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}   		
	
}


//////////////////////////////////////Vendor

public boolean getIsSearchPass() 
{
return isSearchPass;
}
public boolean getSearchVendor(int nVendor){
	
	
	String url = DBURL;
	String userDB =POSTGRESDBNAME;
	String passwordDB=PASSWORDDB;
    String stOffer = SELECTALLFROMOFFER;
 
    
			try {
				
				Connection conOffer=DriverManager.getConnection(url,userDB,passwordDB);;	 
				Statement statementOffer=conOffer.createStatement();					
				ResultSet rsOffer = statementOffer.executeQuery(stOffer);
				while(rsOffer.next()){

					if(rsOffer.getInt(5) == nVendor){
					//	SP_fk_cla =rs_offer.getInt(5);
						return isSearchPass=rsOffer.getBoolean(4);					
					}
				}
				
				return false;
				} catch (SQLException e) {
				e.printStackTrace();
				return false;
			} 
}

////////////////////////////////////////////////////

public boolean goBook(int vID,int cap,String d,int startHourFromUser,int endHourFromUser,String amen,int uID) 
{
boolean ret;
logger.log(Level.INFO,"1-"+doesVenueCapasity(vID,cap)+" \n");
logger.log(Level.INFO,"2-"+doesVenueTime(vID,d,startHourFromUser,endHourFromUser)+"\n");

if(ret= doesVenueCapasity(vID,cap)&&doesVenueTime(vID,d,startHourFromUser,endHourFromUser)&&doesVenueAv(vID)){

	   String url = DBURL;
	   String userDB =POSTGRESDBNAME;
	   String passwordDB=PASSWORDDB;
	   
	 logger.log(Level.INFO,"Choose one of Them as offer you want: \n");	   
	  getListOffer();
	  Scanner s = new Scanner(System.in); 
	   int oId=s.nextInt();
	   boolean choosePass=chooseOffer(oId);
	
	   if(!choosePass) {
		   logger.log(Level.INFO,"Sorry this offer not availabe \n");
		   return false;
		   }
	  
       String st_offer = "insert into event values (Default"+",'noPer','"+d+"',"+"'"+locationVenueCla+"',"+cap+",'"+amen+"',"+uID+","+vID+","+sPFkCla+")";
       
    //amen proplem
    			Connection conEvent;
    			try {
					conEvent = DriverManager.getConnection(url,userDB,passwordDB);	
					Statement statementOffer = conEvent.createStatement();					
			
				
					int x = statementOffer.executeUpdate(st_offer);
					String getIndexLastRow="select * from event";
					Statement stGetRow = conEvent.createStatement();
					ResultSet rsRow=stGetRow.executeQuery(getIndexLastRow);
					rsRow.next();
					int indexEventRow = rsRow.getInt(1);
					setCalender(d,startHourFromUser,endHourFromUser,true,indexEventRow);
							
					 
					
				} catch (SQLException e) {
					e.printStackTrace();
				};	 
		  
     return true;         	
}
else { 
if(!getIsVenueAv()){
	logger.log(Level.INFO,"Sorry Not available \n");
}	
if(!getIsVenueCap()){
	
logger.log(Level.INFO,"Sorry Tha Capasity not enough \n");	
}

if(!getIsVenueTime()){
	logger.log(Level.INFO,"Sorry The Time Overlap \n");	
}
}

return ret;

}
//////////////////////////////////
public boolean setCalender(String d,int s,int e,boolean isA,int indexEventRow) 
{

	LocalTime s2 = LocalTime.of(s,0);
	
	LocalTime e2 = s2.plusHours(e-s);
	
	   String url = DBURL;
	   String userDB =POSTGRESDBNAME;
	   String passwordDB=PASSWORDDB;
	
	   Connection conCalender;
	  		
	try {
		
		
		 String sqlInsertToCalender = "insert into calender values("+"default,"+"'"+d+"',"+"'"+s2+"'"+","+"'"+e2+"'"+","+indexEventRow+","+isA+")";
	    logger.log(Level.INFO,sqlInsertToCalender);
	
		 JOptionPane.showMessageDialog(null,sqlInsertToCalender);
		conCalender = DriverManager.getConnection(url,userDB,passwordDB);
		Statement statementCalender = conCalender.createStatement();					
		 int x = statementCalender.executeUpdate(sqlInsertToCalender); 
			
		 return true;
	} catch (SQLException e1) {
		e1.printStackTrace();
		return false;
	}	
	
 
	
	
}








}
