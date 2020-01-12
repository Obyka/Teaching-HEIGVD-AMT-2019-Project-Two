Feature: CRUD operations for login API

  Background:
    Given there is a Login server

  Scenario: create account
    Given UserToPost payload
    When I POST a login to /users
    Then I received a 201 code status with UserToGet payload

  Scenario: get user
    Given authorization string
    When I GET with authorization string
    Then I received a 201 code status with UserToGet payload