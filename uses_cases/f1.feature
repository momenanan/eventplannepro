Feature: View Calendar

  Scenario: Viewing Empty Calendar
    Given the user opens the calendar application
    When the user views the calendar
    Then they should see a blank calendar with no events scheduled

  Scenario: Viewing Calendar with Events
    Given the user has scheduled events in the calendar
    When the user views the calendar
    Then they should see the calendar with dates highlighted where events are scheduled
    And they should be able to see the details of the single event scheduled for each respective date

  Scenario: Navigating Calendar
    Given the user is viewing the calendar
    When the user wants to see events for a different month or year
    Then they should be able to navigate to the desired month or year using navigation buttons or dropdown menus
    And the calendar should update to display events for the selected month or year

 
 