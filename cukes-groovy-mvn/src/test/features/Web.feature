 Feature: EZProxyTest
 
 Scenario: I login to EZProxy
 Given I go to http://onlinelibrary.wiley.com.ezproxy.aub.edu.lb/license-sources
 Given I enter text libit into input user
 Given I enter text li@104802 into input pass 
 Given I submit user
 Given I see page title Wiley Online Library : License Sources
 