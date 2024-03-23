package eventplanner;


//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import eventplanner.Apps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import junit.framework.Assert;

public class Snippet2 {
    private final Apps application;

    public Snippet2(Apps a1) {
        application = a1;
    }
    public class CalendarMatrixTest {

        @Test
        public void testCalendarMatrix() {
            // Define the expected matrix with the given data
            String[][] expectedMatrix = {
                {"2024-03-10", "12:00:00", "15:00:00", "t"},
                {"2024-03-15", "18:00:00", "22:00:00", "t"},
                {"2024-03-20", "10:00:00", "12:00:00", "t"}
            };

          String actualMatrix[][] =  application.viewCalendar();
            
          boolean flag_is_find = false;
          for (int i = 0; i < expectedMatrix.length; i++) {
              for (int j = 0; j < expectedMatrix[i].length; j++) {
                System.out.printf("1==>"+actualMatrix[i][j]+"  2==>"+expectedMatrix[i][j]+"\n");  
              if(actualMatrix[i][j] != expectedMatrix[i][j]) {            
                		 flag_is_find = false;
              }
                  
                  
              
              }
              if(!flag_is_find) {break;}
              
          }
          
         
          org.junit.Assert.assertTrue(flag_is_find);
        }
    }
    
    
    
    
    
    
    
    
    public void testRetrieveReservationMatrix() {
       
        
        
       
        
        
        
    }
    
    @Given("the user opens the calendar application")
    public void theUserOpensTheCalendarApplication() {
        
    }

    @When("the user views the calendar")
    public void theUserViewsTheCalendar() {
        
    }

    @Then("they should see a blank calendar with no events scheduled")
    public void theyShouldSeeABlankCalendarWithNoEventsScheduled() {
    	
    }

    private void assertTrue(String message, Object checkIfCalendarIsBlank) {
		
		
	}

	
	@Given("the user has scheduled events in the calendar")
    public void theUserHasScheduledEventsInTheCalendar() {
      
    }

    @Then("they should see the calendar with dates highlighted where events are scheduled")
    public void theyShouldSeeTheCalendarWithDatesHighlightedWhereEventsAreScheduled() {
    	
    }

    @Then("they should be able to see the details of the single event scheduled for each respective date")
    public void theyShouldBeAbleToSeeTheDetailsOfTheSingleEventScheduledForEachRespectiveDate() {
    	
    }

    @Given("the user is viewing the calendar")
    public void theUserIsViewingTheCalendar() {
       
    }

    @When("the user wants to see events for a different month or year")
    public void theUserWantsToSeeEventsForADifferentMonthOrYear() {
      
    }

    @Then("they should be able to navigate to the desired month or year using navigation buttons or dropdown menus")
    public void theyShouldBeAbleToNavigateToTheDesiredMonthOrYearUsingNavigationButtonsOrDropdownMenus() {
       
    }

    @Then("the calendar should update to display events for the selected month or year")
    public void theCalendarShouldUpdateToDisplayEventsForTheSelectedMonthOrYear() {
        
    }
 //////////////////////////

@Given("the user is viewing the calendar interface")
public void theUserIsViewingTheCalendarInterface() {
   
}
@When("the user wants to manage tasks or appointments")
public void theUserWantsToManageTasksOrAppointments() {
  
}
@Then("they can create, edit, or delete tasks and appointments directly on the calendar interface")
public void theyCanCreateEditOrDeleteTasksAndAppointmentsDirectlyOnTheCalendarInterface() {
   
}






}
 	