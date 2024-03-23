Feature: offer managment

Scenario: filter offer in range fees
Given i am user
When filter offer in range 
Then filter pass

Scenario: filter offer Not in range fees
Given i am user
When filter offer not in range 
Then filter failed



Scenario: choose offer for event that available
Given i am user
When chose offer and available
Then choose pass

Scenario: choose offer not available
Given i am user
When  choose offer and not available
Then choose failed



