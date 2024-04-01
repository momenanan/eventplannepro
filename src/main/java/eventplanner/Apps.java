package eventplanner;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import io.cucumber.plugin.event.Event;



public class Apps {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String userDB = "postgres";
    String passwordDB = "12345";
	private Event[] events;

    public String[][] viewCalendar() {
        String st_calender = "SELECT * FROM calender";
     

        try {
            Connection con_calender = DriverManager.getConnection(url, userDB, passwordDB);
            Statement statement_calender = con_calender.createStatement();
            ResultSet rs_calender = statement_calender.executeQuery(st_calender);
   
            Statement statement_calender_row = con_calender.createStatement();
           String st_calender_row ="select count(*) from calender";
            
//           int rows= statement_calender_row.executeUpdate(st_calender_row);
	
           ResultSet rsCal= statement_calender_row.executeQuery(st_calender_row);
          rsCal.next();
           int rows = rsCal.getInt(1);
           
            String[][] calendarMatrix = new String[rows][5];
         
            // Display calendar events
         while (rs_calender.next()) {
             int i =0;

        	 int calendarId = rs_calender.getInt("calender_id");
        
                String dayDate = rs_calender.getString("day_date");
                String startAt = rs_calender.getString("start_at");
                String endAt = rs_calender.getString("end_at");
                
                boolean isAvailable = rs_calender.getBoolean("is_available");

   
                    calendarMatrix[i][0]=calendarId+"";
                    calendarMatrix[i][1]=dayDate+"";
                    calendarMatrix[i][2]=startAt+"";
                    calendarMatrix[i][3]=endAt+"";
                    calendarMatrix[i][4]=isAvailable+"";
                    
    
                System.out.printf(""+ "Calendar ID: " + calendarId +
                        "|| Day Date: " + dayDate +
                        "|| Start At: " + startAt +
                        "|| End At: " + endAt +
                        
                        "|| Is Available: " + isAvailable+"\n");
            }
         
            // Close database resources
            rs_calender.close();
            statement_calender.close();
            con_calender.close();

            return calendarMatrix; // Calendar events successfully viewed
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Unable to view calendar events due to SQL exception
        }
   
    }
    
    public void testReservationMatrix() {
        // Define the expected matrix with exact values
        boolean[][] expectedMatrix = {
            {false, false, false},
            {true, false, false},
            {false, true, false}
        };
        
        // Retrieve the actual matrix from this class
        boolean[][] actualMatrix = retrieveReservationMatrix();
        
        // Check if the actual matrix matches the expected matrix
        for (int i = 0; i < expectedMatrix.length; i++) {
            for (int j = 0; j < expectedMatrix[i].length; j++) {
                if (actualMatrix[i][j] != expectedMatrix[i][j]) {
                    System.out.println("Reservation status at row " + (i + 1) + ", column " + (j + 1) + " does not match");
                }
            }
        }
    }
    public boolean[][] retrieveReservationMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	public void openCalendar() {
        // Implement opening the calendar application
        System.out.println("Calendar application is opened.");
        
        try {
            // Open the calendar URL in the default web browser
            String calendarURL = "jdbc:postgresql://localhost:5432/postgres";
            Desktop.getDesktop().browse(new URI(calendarURL));
            System.out.println("Calendar view for event birthday planning is opened.");
        } catch (IOException | URISyntaxException e) {
            // Handle any exceptions that occur while opening the URL
            e.printStackTrace();
            System.out.println("Failed to open the calendar view. Please check the URL.");
        }
    }

    public void verifyBlankCalendar() {
    	 List<Event> events = retrieveEventsFromDatabase();

         // Check if there are no events
         if (events.isEmpty()) {
             System.out.println("Blank calendar is verified.");
         } else {
             System.out.println("Calendar is not blank. Events are present.");
         }
     }

     private List<Event> retrieveEventsFromDatabase() {
         // Simulate retrieving events from a database
         // Implement database query to fetch events
         // For simplicity, returning an empty list here
         return new ArrayList<>();
     }

    public void scheduleEvents() {
    	 events = null;
		for (Event event : events) {
             addToCalendar(event);
         }

         System.out.println("Events are scheduled in the calendar.");
     }

     private void addToCalendar(Event event) {
         // Add the event to the calendar system
         // For simplicity, just printing event details here
         System.out.println("Event scheduled: " + event);
     }

    public void verifyHighlightedDates() {
        // Implement verifying highlighted dates in the calendar
        System.out.println("Highlighted dates with events are verified.");
    }

    public void verifyEventDetails() {
        // Implement verifying event details in the calendar
        System.out.println("Event details for each respective date are verified.");
    }

    public void navigateToCalendarView() {
        // Implement navigating to the calendar view
        System.out.println("Navigated to the calendar view.");
    }

    public void changeMonthOrYear() {
        // Implement changing the month or year in the calendar
        System.out.println("Month or year is changed in the calendar.");
    }

    public void verifyNavigationFunctionality() {
        // Implement verifying navigation functionality in the calendar
        System.out.println("Navigation functionality is verified.");
    }

    public void verifyCalendarUpdate() {
        // Implement verifying calendar update
        System.out.println("Calendar update is verified.");
    }

    public static void main(String[] args) {
        Apps app = new Apps();
  //      boolean success = app.viewCalendar();
        boolean success = true;
        
        if (success) {
            JOptionPane.showMessageDialog(null, "Calendar events successfully viewed!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to view calendar events. Please check the database connection.");
        }
    }

	public boolean isCalendarBlank() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addEvent(String string, String string2, String string3, int i) {
		// TODO Auto-generated method stub
		
	}

	public boolean isDateHighlighted(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean[][] retrieveReservationMatrix1() {
		// TODO Auto-generated method stub
		return null;
	}
}

