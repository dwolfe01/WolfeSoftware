Feature: Google Drive Sharer
Scenario: Access Denied
When I navigate to /
Then I should be on URL: /login

Scenario: Access Denied 2
When I navigate to /upload
Then I should be on URL: /login

Scenario: Incorrect Login
When I navigate to /login
When I enter credentials unknown unknown
Then I should be on URL: /login

Scenario: Login
When I navigate to /login
When I enter credentials username password
Then I should be on URL: /

Scenario: Pics Page
When I navigate to /
Then I see an age
Then I see a timestamp

Scenario: Should Upload File
When I navigate to /login
When I enter credentials username password
When I navigate to /test
Then I see no images
When I navigate to /upload
Then I upload an image test_image.jpeg
When I navigate to /
Then I see an image test_image.jpeg
Then I delete that test_image.jpeg	

Scenario: Should update description
When I navigate to /login
When I enter credentials username password
When I navigate to /test
Then I see no images
When I navigate to /upload
Then I upload an image test_image.jpeg
When I navigate to /
Then I see an image test_image.jpeg
Then I set a description hello world
When I navigate to /
Then I see a description hello world
Then I delete that test_image.jpeg