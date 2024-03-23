Feature: Venue Managment 
Scenario: booking an reserved venue caused by time
Given i am user
When i booking a venue that reserved By Time
Then Booking venue failed


Scenario: booking a venue that number of guist is up maximum
Given i am user  
When i booking a venue capasity not enough
Then Booking venue failed 


Scenario: booking a venue with all perfectly
Given i am user
When i am user and venue is perfectly available
Then booking Succesfully Done


Scenario: user enter wrong realistic time
Given i am user
When i enter Wrong realistic Time
Then booking faild


Scenario: user enter not_valid_time
Given i am user
When i enter not_valid_time
Then booking faild










   