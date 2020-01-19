Feature: CRUD operations for login API

  Background: login as an administrator
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code

  Scenario: get user
    Given valid token
    When I search some information about me
    Then I received all information about me and 200 status code

  Scenario: create a new account and login as normal user and change password
    Given a new user to add
    And a valid administrator token
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code
    Given credentials for normal user
    When I try to login in the system as normal user
    Then I received a valid token with 200 status code
    Given new password
    And valid token for normal user
    When I update my password with a new password
    Then I receive all my information and 200 status code

  Scenario: create a new account, login and try to get information
    Given a new user to add
    And a valid administrator token
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code
    Given credentials for normal user
    When I try to login in the system as normal user
    Then I received a valid token with 200 status code
    Given valid token for normal user
    When I search some information about me
    Then I received all information about me and 200 status code