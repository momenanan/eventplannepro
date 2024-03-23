Feature:

Scenario: search based on availability and pass  
Given i am user
When  search and pass
Then search pass

Scenario: search based on availability and not pass
Given i am user
When search and not find
Then search fieled


Scenario: search based on price and pass  
Given i am user
When  search and pass
Then search pass

Scenario: search based on price and not pass
Given i am user
When search and not find
Then search fieled








