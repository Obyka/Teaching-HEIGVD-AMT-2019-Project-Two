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

  Scenario: login as an administrator, add a new user, login with this new user and try to add a new user with normal user token
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code
    Given a valid administrator token
    And user to add
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code
    Given credentials for normal user
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code
    Given a new user to add
    And valid token for normal added user
    When I try to add a new user with normal user token
    Then the system returns me an error with 403 status code