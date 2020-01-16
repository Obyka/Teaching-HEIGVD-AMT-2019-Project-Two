Feature: CRUD for Trainers
  
  Scenario: add a new trainer and check if is it created
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    When I get information about this trainer
    Then the system returns the added trainer with 200 status