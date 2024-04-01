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

public class Main {

    private static YearMonth currentYearMonth;
    private static Map<LocalDate, List<String>> eventsMap; // Map to store events for each date
    private static UserLoginPage logInPage = new UserLoginPage();
    private static Apps CalendarObj = new Apps();
    
    
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
    	System.out.println("Calendar View:");
        System.out.println("+------------+--------------------------------------------+");
        System.out.println("|    Date    |                   Events                   |");
        System.out.println("+------------+--------------------------------------------+");

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
            System.out.printf("| %-11s| %-42s|\n", formattedDate, eventString.isEmpty() ? "No events" : eventString);

            
            // Move to the next day
            startDate = startDate.plusDays(1);
        }

        // Print calendar footer
        System.out.println("+------------+--------------------------------------------+");
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
        
    	
    	//Application a2 = new Application();
    	//a2.setCalender("2024-05-03",3,4,true);
    	
    	System.out.println("Welcome In Event Planner System \n   " +
                " Plases Choose Role : \n" +
                "1- Admin \n" +
                "2- User \n" +
                "3- Service Provider \n");

        System.out.println("Enter Number 1/2/3 \n");
        Scanner s = new Scanner(System.in);
        //int Choose = s.findInLine(".").charAt(0);
       int Choose = s.nextInt();
System.out.printf("your choose is: "+Choose+"\n");
        if(Choose == 1){
            System.out.println("Welcome In Admin Page" +
                    " Plases Choose Page : \n" +
                    "1-SignIn/Register \n" +
                    "2-SignUP/Login \n");

            System.out.println("Enter Number 1/2 \n");
            Scanner p = new Scanner(System.in);
            int ChoosePage = p.findInLine(".").charAt(0);

            if(ChoosePage==1){

          	  // Register Page
            }

            if(ChoosePage==2){
        
          	  // Login Page
            }

        }
        
        if(Choose == 2){
            System.out.println("Welcome In User Page" +
                    " Plases Choose Page : \n" +
                    "1- SignIn/Register \n" +
                    "2- SignUP/Login \n");


            System.out.println("Enter Number 1/2 in user page \n");
            Scanner p = new Scanner(System.in);
            //int ChoosePage = p.findInLine(".").charAt(0);
          int ChoosePage = p.nextInt();
            System.out.printf("chose page is "+ ChoosePage+"\n");
            if(ChoosePage==1){
          	  // Register Page
            }

            if(ChoosePage==2){
          	  // Login Page
      System.out.printf("helo log in user: \n");           
                 
          	  System.out.printf("please enter UserName:+\n");              
                 Scanner ScannerUserName = new Scanner(System.in);
                 String LineUserName=ScannerUserName.nextLine();
                 System.out.printf("userName you enter is : "+LineUserName+"\n"); 
                     
                 System.out.printf("please enter password:+\n");              
                 Scanner ScannerPassword = new Scanner(System.in);
                 String LinePassword=ScannerUserName.nextLine();
                System.out.printf("your password enter is :"+ LinePassword+"\n"); 
        
                try {	
                	
                	boolean t;
                    if(t = (logInPage.is_valid_credentials(LineUserName,LinePassword,"users")))
                    {
                    	int userID=logInPage.getUID();
//                    	t = (logInPage.is_valid_credentials(LineUserName,LinePassword,"users"))
                    	System.out.printf("log in flag: "+t);
                    	System.out.printf("Please choose on of these operation: \n");
                      Scanner chooseFromLogIn=new Scanner(System.in);
                      
                      System.out.printf("1- create Event:- "+"\n"+"2-");
 
                      int x = chooseFromLogIn.nextInt();
                      if(x == 1){
                    	 Application a = new Application();
                                         	 
                   System.out.printf("enter The venue you want:\n");	
                         int v1= chooseFromLogIn.nextInt();
                   System.out.printf("enter The Number of guist:\n");	
                         int g1=chooseFromLogIn.nextInt(); 
                   System.out.printf("enter year:\n");	
                  Main m = new Main();       
                  Apps Ap1=new Apps();
                  Ap1.viewCalendar();
                  System.out.printf("These wich are  time you can't booking:\n"); 
                  int y = chooseFromLogIn.nextInt(); 
                  System.out.printf("Enter month please:\n");
                  int m1 = chooseFromLogIn.nextInt();               
                  System.out.printf("Enter Day please\n");
                   int d1 = chooseFromLogIn.nextInt();
                   
                   String Da = y+"-"+m1+"-"+d1;
                   
                   //////////////////////
                         System.out.printf("enter hour to start event:\n");
                         int s1 = chooseFromLogIn.nextInt(); 
                         System.out.printf("enter hour to end event:\n");
                         int e1 = chooseFromLogIn.nextInt();
                         ///////////////////
                   System.out.printf("enter amenetis:\n");
                   String a1 = chooseFromLogIn.nextLine();
                   
                   ////////////////////
    
                   a.Go_book(v1,g1,Da,s1,e1,a1,userID);                    	
    
                      }
                      
                    	
                    }else {
        
                    	System.out.printf("Sorry Log In faild");
                    }
                    
			 	    
                                           	
                
                } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }

        }

        
        
        if(Choose == 3){
            System.out.println("Welcome In Service Provider Page" +
                    " Plases Choose Page : \n" +
                    "1- SignIn/Register \n" +
                    "2- SignUP/Login \n");


            System.out.println("Enter Number 1/2 \n");
            Scanner p = new Scanner(System.in);
            int ChoosePage = p.findInLine(".").charAt(0);


            if(ChoosePage==1){
                // Register Page
            }

            if(ChoosePage==2){
                // Login Page
            }
        
            System.out.println("Wasan awwade" );
            System.out.println("Wasan awwade" );
        
        }

   
  		
    	
/////////////////////////////////////////////////////////    	

    
/////////////////////////
    /* 
    Main calendar = new Main();
        Scanner scanner = new Scanner(System.in);

        // View the calendar
        calendar.viewCalendar();

        // Input new task or appointment
        System.out.println("Enter task/appointment details:");
        System.out.print("Date (YYYY-MM-DD): ");
        String dateString = scanner.next();
        System.out.print("Time (HH:mm AM/PM): ");
        String time = scanner.next();
        System.out.print("Description: ");
        String description = scanner.next();

        // Parse input and create task
        LocalDate taskDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        calendar.createTask(taskDate, time, description);

        // View updated calendar
        calendar.viewCalendar();

        // Input task details to edit
        System.out.println("Enter task/appointment details to edit:");
        System.out.print("Date (YYYY-MM-DD): ");
        String editDate = scanner.next();
        System.out.print("Time (HH:mm AM/PM): ");
        String editTime = scanner.next();
        System.out.print("Description: ");
        String editDescription = scanner.next();
        scanner.nextLine(); // Consume newline character

        // Input new task details
        System.out.println("Enter new task/appointment details:");
        System.out.print("Date (YYYY-MM-DD): ");
        String newDateString = scanner.next();
        System.out.print("Time (HH:mm AM/PM): ");
        String newTime = scanner.next();
        System.out.print("Description: ");
        String newDescription = scanner.next();

        // Parse input and edit task
        LocalDate editTaskDate = LocalDate.parse(editDate, DateTimeFormatter.ISO_DATE);
        calendar.editTask(editTaskDate, editTime, editDescription, newTime, newDescription);

        // View updated calendar
        calendar.viewCalendar();

        scanner.close();
    */
    }
}
    
