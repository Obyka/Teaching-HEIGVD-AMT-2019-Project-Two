Feature: CRUD operations for login API

  Background:
    Given there is a Login server

  Scenario: login as an administrator
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code

  Scenario: create an account
    Given a new user to add
    And a valid administrator token
    When I insert a this new user in the database
    Then I received with the new user who have just insered

  Scenario: get user
    Given valid token
    When I search some information about me
    Then I received all information about me

  Scenario: change password
    Given a valid token
    And new password
    When I update my password with a new password
    Then I receive all about my information