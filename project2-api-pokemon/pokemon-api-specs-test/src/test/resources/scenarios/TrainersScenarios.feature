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

  Scenario: get all trainers belong to administrator
    When I get all trainers
    Then I receive all my trainers belong to me and 200 status code

  Scenario: create and delete a trainer
    Given a new trainer
    When I add a new trainer
    Then the system returns the added pokemon with 201 status
    When I delete this trainer
    Then the system returns the 204 status

  Scenario: create and update a trainer
    Given a new trainer
    When I add a new trainer
    Then the system returns the added pokemon with 201 status
    Given modification on trainer
    When I update a trainer
    Then the system returns the 200 status

  Scenario Outline: pagination
    When I get trainers at specific page <page> with specific size <size>
    Then the system returns the 200 status and size <answer> with trainers

    Examples:
      | page      | size | answer |
      | 0         | 5   |5   |
      | 0         | 10   |10   |
      | 1          | 5   |5   |
      | 0          | 1   |1   |

  Scenario: pagination
    When I get trainers at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code with trainers