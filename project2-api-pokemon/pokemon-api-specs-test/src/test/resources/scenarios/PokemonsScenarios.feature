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