Feature: CRUD operations for login API

  Background: login as an administrator
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code

  Scenario: create an account
    Given a new user to add and a valid administrator token
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code

  Scenario: get user
    Given valid token
    When I search some information about me
    Then I received all information about me and 200 status code

  Scenario: login as normal user and change password
    Given credentials for normal user
    And new password
    When I try to login in the system as normal user
    And I update my password with a new password
    Then the system returns me a token with my identifior and a 200 status code
    And I receive all my information and 200 status code