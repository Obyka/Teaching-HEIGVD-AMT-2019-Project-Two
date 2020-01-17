Feature: CRUD for Pokemons

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token

  Scenario: create a pokemon and check if it is created
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given This new pokemon
    When I get information about this pokemon
    Then the system returns the added pokemon with 200 status

  Scenario: create a pokemon and a user and login and try to get pokemon with this user
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given information for new user and other parameters
    When I insert this new user
    Then The user has been created
    Given credentials for new user
    When I make a POST request to /api/login/login
    Then I receive a valid token
    When I try to get the pokemon with this new user
    Then The system returns me an error with 404 status code
    
  Scenario: get a pokemon that does not exist
    Given random pokeDexId
    When I get information about this pokemon
    Then The system returns me an error with 404 status code

  Scenario: get all pokemons belong to administrator
    When I get all pokemons
    Then I receive all my pokemons belong to me and 200 status code
    
  Scenario: create and delete a pokemon
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    When I delete this pokemon
    Then the system returns the 204 status

  Scenario: create and update a pokemon
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given modification on Pokemon
    When I update a pokemon
    Then the system returns the 200 status

  Scenario Outline: pagination
    When I get pokemons at specific page <page> with specific size <size>
    Then the system returns the 200 status and size <answer>

  Examples:
    | page      | size | answer |
    | 0         | 20   |20   |
    | 0         | 10   |10   |
    | 1          | 10   |10   |
    | 0          | 1   |1   |

  Scenario: pagination
    When I get pokemons at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code
