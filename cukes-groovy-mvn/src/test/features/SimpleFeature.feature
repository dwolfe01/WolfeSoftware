 Feature: Cucumber salesman
 
 Scenario: I sell some of my cucumbers
 Given I start with 12 cucumbers
 When I sell 3 cucumbers
 Then I have 9 cucumbers
 
 Scenario: I sell some of my cucumbers
 Given I start with 8 cucumbers
 When I sell 8 cucumbers
 Then I have 0 cucumbers