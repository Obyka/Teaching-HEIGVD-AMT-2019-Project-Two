Feature: testing error status code for every API operations

  Scenario: login with bad credentials
    Given bad credentials
    When I try to connect with bad credentials
    Then the system returns me an error that it is a bad login with 401 status code

  Scenario: get information about me with empty token
    Given empty token
    When I try to get information with empty token
    Then the system returns me an error with 403 status code

  Scenario: Add user without administrator token
    Given empty token
    And user to add
    When I try to add a new user without token
    Then the system returns me an error that I have denied access with 403 status code