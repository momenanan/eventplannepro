package eventplanner;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static YearMonth currentYearMonth;
    private static Map<LocalDate, List<String>> eventsMap; // Map to store events for each date
    private static UserLoginPage logInPage = new UserLoginPage();
    private static Registration registration = new Registration();
    
    private static Apps calendarObj = new Apps();
	 private static final Logger logger = Logger.getLogger(Main.class.getName());
	    private static final String SEPARATOR_LINE = "+------------+--------------------------------------------+";


    
    
    //////////////////////////////////////////////////////////
    // Database connection details
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String userDB = "postgres";
    private String passwordDB = "12345";

    public Main() {
        currentYearMonth = YearMonth.now();
        eventsMap = new HashMap<>();
        // Add some sample upcoming events for demonstration
        addSampleEvents();
    }

    private void addSampleEvents() {
        // Add sample events for demonstration
        List<String> events1 = new ArrayList<>();
        events1.add("Event 1 at 10:00 AM");
        events1.add("Event 2 at 2:00 PM");
        eventsMap.put(LocalDate.now().plusDays(2), events1);

        List<String> events2 = new ArrayList<>();
        events2.add("Event 3 at 9:30 AM");
        eventsMap.put(LocalDate.now().plusDays(5), events2);

        List<String> events3 = new ArrayList<>();
        events3.add("Event 4 at 3:00 PM");
        events3.add("Event 5 at 6:00 PM");
        eventsMap.put(LocalDate.now().plusDays(10), events3);
    }

    public static void viewCalendar() {
    	 logger.log(Level.INFO,"Calendar View:");
        logger.log(Level.INFO,SEPARATOR_LINE);
        logger.log(Level.INFO,"|    Date    |                   Events                   |");
        logger.log(Level.INFO,SEPARATOR_LINE);

        // Iterate over each day in the month
        LocalDate startDate = currentYearMonth.atDay(1);
        
        LocalDate endDate = currentYearMonth.atEndOfMonth();
        
        while(!startDate.isAfter(endDate)) {
            // Format date
            String formattedDate = startDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));

            
            // Check if the date has any events
            List<String> events = eventsMap.getOrDefault(startDate, new ArrayList<>());
            String eventString = String.join("\n", events);

            
            // Print date and events
		        String output = String.format("| %-11s| %-42s|%n", formattedDate, eventString.isEmpty() ? "No events" : eventString);
		        logger.log(Level.INFO,output);
            
            // Move to the next day
            startDate = startDate.plusDays(1);
        }

        // Print calendar footer
  logger.log(Level.INFO,SEPARATOR_LINE);
    }

    public void createTask(LocalDate date, String time, String description) {
        // Parse the time string
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes;
        String period;

        if (timeParts.length > 1) {
            String[] minutesParts = timeParts[1].split(" ");
            minutes = Integer.parseInt(minutesParts[0]);
            period = minutesParts.length > 1 ? minutesParts[1] : ""; // Handle case when AM/PM indicator is not provided
        } else {
            minutes = 0;
            period = ""; // No AM/PM indicator provided
        }

        if (period.equalsIgnoreCase("AM")) {
            if (hours == 12) {
                hours = 0;
            }
        } else if (period.equalsIgnoreCase("PM")) {
            if (hours != 12) {
                hours += 12;
            }
        }

        LocalTime localTime = LocalTime.of(hours, minutes);

        // Convert LocalTime to Time
        java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);

        // Save task to events map
        eventsMap.computeIfAbsent(date, k -> new ArrayList<>()).add("Task at " + sqlTime + ": " + description);
    }

    public void editTask(LocalDate date, String time, String description, String newTime, String newDescription) {
        // Remove the existing task
        if (eventsMap.containsKey(date)) {
            List<String> events = eventsMap.get(date);
            String taskToRemove = "Task at " + time + ": " + description;
            events.remove(taskToRemove);

            // Add the edited task
            createTask(date, newTime, newDescription);
        }
    }

    public static void main(String[] args) {
        
    	logger.log(Level.INFO,"Welcome In Event Planner System \n   " +
                " Plases Choose Role : \n" +
                "1- Admin \n" +
                "2- User \n" +
                "3- Service Provider \n");

        logger.log(Level.INFO,"Enter Number 1/2/3 \n");
        Scanner s = new Scanner(System.in);
       int choose = s.nextInt();
   logger.log(Level.INFO,"your choose is: "+choose+"\n");
        if(choose == 1){
            logger.log(Level.INFO,"Welcome In Admin Page" +
                    " Plases Choose Page : \n" +
                    "1-SignIn/Register \n" +
                    "2-SignUP/Login \n");

           logger.log(Level.INFO,"Enter Number 1/2 \n");
            Scanner p = new Scanner(System.in);
            int choosePage = p.findInLine(".").charAt(0);

            if(choosePage==1){

        
            	
         	   logger.log(Level.INFO,"Enter FirstName Admin: \n");
        	   String FN = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter SecName Admin: \n");
        	   String SN = p.nextLine(); 
        	  
        	   logger.log(Level.INFO,"Enter LasttName Admin: \n");
        	   String LN = p.nextLine(); 
        	  
        	   logger.log(Level.INFO,"Enter email  Admin :\n");
        	   String Eamail = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter UserName Admin \n");
        	   String userName = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter password Admin \n");
        	   String password = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter Date: \n");
        	   String DateOfUser = p.nextLine();         	   
        	   
			try {
				registration.setData(FN,SN,LN,Eamail,userName,password,DateOfUser,"admin");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          	  // Register Page
            }

            if(choosePage==2){
        

            	
       
            	
            	
          	  // Login Page
            }

        }
        //////////////////////////////////Now User
        
        if(choose == 2){
            logger.log(Level.INFO,"Welcome In User Page" +
                    " Plases Choose Page : \n" +
                    "1- SignIn/Register \n" +
                    "2- SignUP/Login \n");


            logger.log(Level.INFO,"Enter Number 1/2 in user page \n");
            Scanner p = new Scanner(System.in);
            //int ChoosePage = p.findInLine(".").charAt(0);
          int choosePage = p.nextInt();
            logger.log(Level.INFO,"chose page is "+ choosePage+"\n");
            if(choosePage==1){
            	
           try {
        	  logger.log(Level.INFO,"Enter FirstName: \n");
        	   String FN = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter SecName: \n");
        	   String SN = p.nextLine(); 
        	  
        	   logger.log(Level.INFO,"Enter LasttName: \n");
        	   String LN = p.nextLine(); 
        	  
        	  logger.log(Level.INFO,"Enter email: \n");
        	   String Eamail = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter UserName: \n");
        	   String userName = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter password: \n");
        	   String password = p.nextLine(); 
        	   
        	   logger.log(Level.INFO,"Enter Date: \n");
        	   String DateOfUser = p.nextLine();         	   
        	   
			registration.setData(FN,SN,LN,Eamail,userName,password,DateOfUser,"users");
		
           
           } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
            	
          	  // Register Page
            }

            if(choosePage==2){
          	  // Login Page
      System.out.printf("helo log in user: \n");           
                 
          	  logger.log(Level.INFO,"please enter UserName:+\n");              
                 Scanner ScannerUserName = new Scanner(System.in);
                 String LineUserName=ScannerUserName.nextLine();
                logger.log(Level.INFO,"userName you enter is : "+LineUserName+"\n"); 
                     
                 logger.log(Level.INFO,"please enter password:+\n");              
                 Scanner ScannerPassword = new Scanner(System.in);
                 String LinePassword=ScannerUserName.nextLine();
                logger.log(Level.INFO,"your password enter is :"+ LinePassword+"\n"); 
        
                try {	
                	
                	boolean t;
                    if(t = (logInPage. isValidCredentials(LineUserName,LinePassword,"users")))
                    {
                    	int userID=logInPage.getUID();
//                    	t = (logInPage.is_valid_credentials(LineUserName,LinePassword,"users"))
                    	logger.log(Level.INFO,"log in flag: "+t);
                    	logger.log(Level.INFO,"Please choose on of these operation: \n");
                      Scanner chooseFromLogIn=new Scanner(System.in);
                      
                      logger.log(Level.INFO,"1- create Event:- "+"\n");
 
                      int x = chooseFromLogIn.nextInt();
                      if(x == 1){
                    	 Application a = new Application();
                                         
                    	 logger.log(Level.INFO,"if you want to filter venue press f \n");
                    	 Scanner ch=new Scanner(System.in);
                    	 String fa = ch.nextLine();
                    	 
                    	
                    	 if(fa.equals("f")) {
                    		logger.log(Level.INFO,"Enter min price:\n");
                    		 int min1 = chooseFromLogIn.nextInt();
                    		 
                    		 logger.log(Level.INFO,"Enter max price:\n");
                    		 int max1 = chooseFromLogIn.nextInt();
                    		 
                    		 a.filter_price_venue(min1, max1);
                    
                    		 
                    	 }
                   //////////////////////////////////
                    	 
                    	 logger.log(Level.INFO,"if you want to filter offer press f \n");
                    	 String fa2 = ch.nextLine();
                              	 
                    	 if(fa2.equals("f")) {
                    		 logger.log(Level.INFO,"Enter min price:\n");
                    		 int min1 = chooseFromLogIn.nextInt();
                    		 
                    		 logger.log(Level.INFO,"Enter max price:\n");
                    		 int max1 = chooseFromLogIn.nextInt();
                    		 
                    		 a.filter_price_offer(min1, max1);
                    
                    		 
                    	 }
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                    	 
                  /////////////////////////////////////  	 
                   logger.log(Level.INFO,"enter The venue you want:\n");	
                         int v1= chooseFromLogIn.nextInt();
                  logger.log(Level.INFO,"enter The Number of guist:\n");	
                         int g1=chooseFromLogIn.nextInt(); 
                   logger.log(Level.INFO,"enter year:\n");	
                  Main m = new Main();       
                  Apps Ap1=new Apps();
                  Ap1.viewCalendar();
                  logger.log(Level.INFO,"These wich are  time you can't booking:\n"); 
                  int y = chooseFromLogIn.nextInt(); 
                  logger.log(Level.INFO,"Enter month please:\n");
                  int m1 = chooseFromLogIn.nextInt();               
                  logger.log(Level.INFO,"Enter Day please\n");
                   int d1 = chooseFromLogIn.nextInt();
                   
                   String Da = y+"-"+m1+"-"+d1;
                   
                   //////////////////////
                         logger.log(Level.INFO,"enter hour to start event:\n");
                         int s1 = chooseFromLogIn.nextInt(); 
                         logger.log(Level.INFO,"enter hour to end event:\n");
                         int e1 = chooseFromLogIn.nextInt();
                         ///////////////////
                   logger.log(Level.INFO,"enter amenetis:\n");
                   String a1 = chooseFromLogIn.nextLine();
                   
                   ////////////////////////    
                   a.Go_book(v1,g1,Da,s1,e1,a1,userID);                    	
                   
                      }                    	
                    }else
                    {
        
                    	logger.log(Level.INFO,"Sorry Log In faild");
                    }
                    
			 	    
                                           	
                
                } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }

        }

        
        
        if(choose == 3){
            logger.log(Level.INFO,"Welcome In Service Provider Page" +
                    " Plases Choose Page : \n" +
                    "1- SignIn/Register \n" +
                    "2- SignUP/Login \n");


            logger.log(Level.INFO,"Enter Number 1/2 \n");
            Scanner p = new Scanner(System.in);
            int ChoosePage = p.findInLine(".").charAt(0);


            if(ChoosePage==1){
                // Register Page
            }

            if(ChoosePage==2){
                // Login Page
            }
        
            logger.log(Level.INFO,"Wasan awwade" );
            logger.log(Level.INFO,"Wasan awwade" );
        
        }


    }
}
    
