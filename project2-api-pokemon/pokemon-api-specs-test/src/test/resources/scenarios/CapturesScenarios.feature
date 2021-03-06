Feature: CRUD operations for captures

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token

  Scenario: add pokemons and trainer and captures and test pagination
    Given a new trainer
    When I add 20 pokemons
    And I add a new trainer
    And I add 20 captures about a trainer
    And I get captures about a trainer at specific page 0 with specific size 20
    Then the system returns the 200 status and size 20 with captures
    When I get captures about a trainer at specific page 0 with specific size 10
    Then the system returns the 200 status and size 10 with captures
    When I get captures about a trainer at specific page 1 with specific size 10
    Then the system returns the 200 status and size 10 with captures
    When I get captures about a trainer at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code

  Scenario: add pokemon and trainers and captures and test pagination
    Given a new pokemon to create
    When I add a new pokemon
    And I create 20 trainers
    And I add 20 captures about a pokemon
    And I get captures about a pokemon at specific page 0 with specific size 20
    Then the system returns the 200 status and size 20 with captures
    When I get captures about a pokemon at specific page 0 with specific size 10
    Then the system returns the 200 status and size 10 with captures
    When I get captures about a pokemon at specific page 1 with specific size 10
    Then the system returns the 200 status and size 10 with captures

  Scenario: create a pokemon, a trainer and a capture and check if it is created
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    Given a new capture
    When I create a new capture
    Then the system returns the added capture with 201 status
    When I get information about this capture with the new pokemon
    Then the system returns the added capture with pokemon with 200 status
    When I get information about this capture with the new trainer
    Then the system returns the added capture with trainer with 200 status

  Scenario: get captures with a pokemon that does not exist
    Given random pokeDexId
    When I get information about captures with a unexisted pokemon
    Then The system returns me an error with 404 status code

  Scenario: get captures with a trainer that does not exist
    Given random trainer ID
    When I get information about captures with a unexisted trainer
    Then The system returns me an error with 404 status code