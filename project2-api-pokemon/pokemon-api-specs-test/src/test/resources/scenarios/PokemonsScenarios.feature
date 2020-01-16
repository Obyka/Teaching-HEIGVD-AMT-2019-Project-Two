Feature: CRUD for Pokemons

  Scenario: create a pokemon and check if it is created
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given This new pokemon
    When I get information about this pokemon
    Then the system returns the added pokemon with 200 status