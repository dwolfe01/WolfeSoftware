 Feature: QAP2 test
 
 Scenario: I check all tomcats on QAP2
 Given I go to http://10.6.230.12:9091/
 Given I go to http://10.6.230.12:9092/
 Given I go to http://10.6.230.12:9093/
 Given I go to http://10.6.230.12:9094/
 Given I go to http://10.6.230.20:9091/
 Given I go to http://10.6.230.20:9092/
 Given I go to http://10.6.230.20:9093/
 Given I go to http://10.6.230.20:9094/
 Given I go to http://10.6.230.28:9091/
 Given I go to http://10.6.230.28:9092/
 Given I go to http://10.6.230.28:9093/
 Given I go to http://10.6.230.28:9094/
 Given I go to http://10.6.230.36:9091/
 Given I go to http://10.6.230.36:9092/
 Given I go to http://10.6.230.36:9093/
 Given I go to http://10.6.230.36:9094/
 Then I close the browser