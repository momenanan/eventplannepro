package eventplanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.Test;

import eventplanner.Application;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestingForProject {
private boolean flag;
private final Application application;
private boolean flag_find_element;

public TestingForProject(Application app){		
	this.application = app;	
}

@Given("i am user")
public void iAmUser() {
	application.SetWhoLogIn("users");
assertEquals(true,application.getWhoLogIn());	
}

@When("i booking a venue that reserved By Time")
public void iBookingAVenueThatReservedByTime() {
	 application.Does_venue_time(1,"2026-10-02",2,3);
	 assertFalse(application.get_is_venue_time());
}


@Then("Booking venue failed")
public void bookingVenueFailed() {
	application.Does_venue_time(1,"2026-10-02",2,3);
	application.Does_venue_av(2);
	application.Does_venue_capasity(1, 1000);

	assertFalse(application.get_is_venue_time()&&application.get_is_venue_av()&&application.get_is_venue_cap());

}


@When("i booking an reserved venue")
public void iBookingAnReservedVenue() {
	application.Does_venue_av(2);
	 assertFalse(application.get_is_venue_av());	
}

@When("i booking a venue capasity not enough")
public void iBookingAVenueCapasityNotEnough() {
application.Does_venue_capasity(1,100);
assertTrue(application.get_is_venue_cap());

}
@When("i am user and venue is perfectly available")
public void iAmUserAndVenueIsPerfectlyAvailable() {

   application.Does_venue_time(1,"2026-10-02",7,8);
   application.Does_venue_av(1);
   application.Does_venue_capasity(1, 25);

	assertTrue(application.get_is_venue_time()&&application.get_is_venue_av()&&application.get_is_venue_cap());
}
@Then("booking Succesfully Done")
public void bookingSuccesfullyDone() {
	
	//application.Does_venue_time(1,"2026-10-02",7,8);
	//assertTrue(application.is_book_venue_pass(application.Does_venue_av(1),application.get_is_venue_time(),application.Does_venue_capasity(1,25)));

}


@When("i enter Wrong realistic Time")
public void iEnterWrongRealisticTime() {
assertTrue(application.time_realistic(13,15));
}


@Then("booking faild")
public void bookingFaild() {

}

//public void notSameVenue(){
//assertTrue(application.same_venue(2,2));	
//}

//@When("i enter not_valid_time")
//public void iEnterNotValidTime() {
//assertTrue(application.not_valid_time(2, 4,2, 3));

@When("i enter not_valid_time")
public void iEnterNotValidTime() {

	assertTrue(application.not_valid_time(3,6,5,8));

}



///////////////////////////////////////////////////////////////////////////////// offer

@When("filter offer in range")
public void filterOfferInRange() {
	
ArrayList <Integer> realA = new ArrayList<Integer>();	
ArrayList <Integer> ExpectedA = new ArrayList<Integer>();	
ExpectedA.add(150);
ExpectedA.add(300);
ExpectedA.add(200);
ExpectedA.add(350);

realA = application.filter_price_offer(130,359);
boolean flag_find_element = false ;

//JOptionPane.showMessageDialog(null,"1- The value i put: "+ExpectedA);

//JOptionPane.showMessageDialog(null,"2- The value i check: "+realA);

//JOptionPane.showMessageDialog(null,"Excpected: is  "+ExpectedA+" // real: is  "+realA);

for(int i =0;i<realA.size();i++) {
int temp = realA.get(i);	
flag_find_element = false;	
for(int c =0;c<ExpectedA.size();c++) {
		
		if(ExpectedA.get(c)==temp){
 flag_find_element = true;
		}
	}
	if(!flag_find_element){
		break;
	}
	
}
assertTrue(flag_find_element);

}

@Then("filter pass")
public void filterPass() {
assertTrue(application.return_is_filter_offer());	
}

@When("filter offer not in range")
public void filterOfferNotInRange() {
	
	ArrayList <Integer> realA = new ArrayList<Integer>();	
	ArrayList <Integer> ExpectedA = new ArrayList<Integer>();	
	ExpectedA.add(150);
	ExpectedA.add(300);
	ExpectedA.add(200);


	realA = application.filter_price_offer(130,359);
	boolean flag_find_element = false ;

	//JOptionPane.showMessageDialog(null,"1- The value i put: "+ExpectedA);

	//JOptionPane.showMessageDialog(null,"2- The value i check: "+realA);

	//JOptionPane.showMessageDialog(null,"Excpected: is  "+ExpectedA+" // real: is  "+realA);

	for(int i =0;i<realA.size();i++) {
	int temp = realA.get(i);	
	flag_find_element = false;	
	for(int c =0;c<ExpectedA.size();c++) {
			
			if(ExpectedA.get(c)==temp){
	 flag_find_element = true;
			}
		}
		if(!flag_find_element){
			break;
		}
		
	}
	assertFalse(flag_find_element);
				
}

@Then("filter failed")
public void filterFailed() {

assertFalse(flag_find_element);		

}

@When("chose offer and available")
public void choseOfferAndAvailable() {
	
	assertTrue(application.choose_offer(5));
	
}


@Then("choose pass")
public void choosePass() {	
assertTrue(application.get_is_choose_pass());	
//assertTrue(application.return_offer_av());
}

@When("choose offer and not available")
public void chooseOfferAndNotAvailable() {
assertFalse(application.choose_offer(6));	
}
@Then("choose failed")
public void chooseFailed() {
assertFalse(application.return_offer_av());
}
///////////////////////////////////////////////////////////////////// Vendor
@When("search and pass")
public void searchAndPass() {
assertTrue(application.get_search_vendor(1));
}

@Then("search pass")
public void searchPass() {
assertTrue(application.get_is_search_pass());

}
@When("search and not find")
public void searchAndNotFind() {
	assertFalse(application.get_search_vendor(2));	
}



@Then("search fieled")
public void searchFieled() {
	assertFalse(application.get_is_search_pass());

}
}



