Feature: Google Drive Sharer

Scenario: Access Denied
When I navigate to /
Then I should be on URL: /login

Scenario: Incorrect Login
When I navigate to /login
When I enter credentials unknown unknown
Then I should be on URL: /login

Scenario: Login
When I navigate to /login
When I enter credentials username password
Then I should be on URL: /

Scenario: showDateOfBirth
When I navigate to /login
When I enter credentials username password
Then I should be on URL: /
Then I see an age

