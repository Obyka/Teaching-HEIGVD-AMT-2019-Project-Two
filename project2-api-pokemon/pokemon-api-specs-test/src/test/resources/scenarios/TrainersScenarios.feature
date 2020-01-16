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