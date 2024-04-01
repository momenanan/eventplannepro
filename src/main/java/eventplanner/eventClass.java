package eventplanner;

import java.sql.Date;

public class EventClass {

    private int eventId;
    private String periodOfEvent;
    private Date dateEvent;
    private String location;
    private int numGuests;
    private String description;
    private int userForeignKey;
    private int venueForeignKey;
    private int serviceProviderForeignKey;

    // Constructors
    public EventClass() {
        // Default constructor
    }    

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

   

    public void setPeriodOfEvent(String periodOfEvent) {
        this.periodOfEvent = periodOfEvent;
    }

   

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    
    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserForeignKey(int userForeignKey) {
        this.userForeignKey = userForeignKey;
    }   

    public void setVenueForeignKey(int venueForeignKey) {
        this.venueForeignKey = venueForeignKey;
    }

    public void setServiceProviderForeignKey(int serviceProviderForeignKey) {
        this.serviceProviderForeignKey = serviceProviderForeignKey;
    }
}
