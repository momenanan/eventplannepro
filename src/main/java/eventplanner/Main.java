package eventplanner;


import java.sql.Connection;
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

public class Main {

    private YearMonth currentYearMonth;
    private Map<LocalDate, List<String>> eventsMap; // Map to store events for each date

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

    public void viewCalendar() {
    	System.out.println("Calendar View:");
        System.out.println("+------------+--------------------------------------------+");
        System.out.println("|    Date    |                   Events                   |");
        System.out.println("+------------+--------------------------------------------+");

        // Iterate over each day in the month
        LocalDate startDate = currentYearMonth.atDay(1);
        LocalDate endDate = currentYearMonth.atEndOfMonth();

        while (!startDate.isAfter(endDate)) {
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
    }
}
    