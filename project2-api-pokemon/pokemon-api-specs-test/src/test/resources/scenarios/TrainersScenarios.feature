Feature: CRUD for Trainers

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token

  Scenario: add a new trainer and check if is it created
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    When I get information about this trainer
    Then the system returns the added trainer with 200 status

  Scenario: create a trainer and a user and login and try to get pokemon with this user
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    Given information for new user and other parameters
    When I insert this new user
    Then The user has been created
    Given credentials for new user
    When I make a POST request to /api/login/login
    Then I receive a valid token
    When I get information about this trainer
    Then The system returns me an error with 404 status code
    
  Scenario: get a trainer that does not exist
    Given random trainer ID
    When I get information about this trainer
    Then The system returns me an error with 404 status code