Feature: creation of a login
  Creation of a login that returned a JWT token

  Background:
    Given there is a Login server

  Scenario: create account
    Given UserToPost payload
    When I POST a login to /users
    Then I received a 201 code status with UserToGet payload
