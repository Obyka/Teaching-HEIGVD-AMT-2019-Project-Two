Feature: other errors

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token
  
  Scenario: insert a bad payload
    Given malformed data to insert
    When I try to insert a bad payload in pokemon
    Then the system returns the 400 status with an error