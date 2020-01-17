Feature: CRUD operations for captures

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token

  Scenario: add pokemons and trainer
    When I add 20 pokemons
    And I create 20 trainers
    And I add 20 captures

  Scenario Outline: pagination
    And I get captures about a trainer at specific page <page> with specific size <size>
    Then the system returns the 200 status and size <answer> with captures

    Examples:
      | page      | size | answer |
      | 0         | 20   |20   |
      | 0         | 10   |10   |
      | 1          | 10   |10   |
      | 0          | 1   |1   |

  Scenario: pagination
    When I get pokemons at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code

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

  Scenario: create a pokemon, a trainer, a capture and a user and login and try to get capture with this user
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    Given information for new user and other parameters
    When I insert this new user
    Then The user has been created
    Given a new capture
    When I create a new capture
    Then the system returns the added capture with 201 status
    Given credentials for new user
    When I make a POST request to /api/login/login
    Then I receive a valid token
    When I try to get the capture about the new pokemon and this new user
    Then The system returns me an error with 404 status code
    When I try to get the capture about the new trainer and this new user
    Then The system returns me an error with 404 status code


  Scenario: get captures with a pokemon that does not exist
    Given random pokeDexId
    When I get information about captures with a unexisted pokemon
    Then The system returns me an error with 404 status code

  Scenario: get captures with a trainer that does not exist
    Given random trainer ID
    When I get information about captures with a unexisted trainer
    Then The system returns me an error with 404 status code