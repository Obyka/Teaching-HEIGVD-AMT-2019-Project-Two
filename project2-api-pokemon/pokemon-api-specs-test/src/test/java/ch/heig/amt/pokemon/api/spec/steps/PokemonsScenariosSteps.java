package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PokemonsScenariosSteps {
    private Environment environment;
    private Integer pokemonId;

    private String lastMilliseconds = "" + System.currentTimeMillis();
    private int lastMillisecondsInt = Math.abs((int)System.currentTimeMillis());

    public PokemonsScenariosSteps(Environment environment) {
        this.environment = environment;
    }

    @Given("^a new pokemon to create$")
    public void a_new_pokemon_to_create() throws Throwable {
        environment.getPokemonPut().setPokedexId(lastMillisecondsInt);
        environment.getPokemonPut().setName("Pokemon"+lastMilliseconds);
        environment.getPokemonPut().setCategory("Tester");
        environment.getPokemonPut().setType("Testing");
        environment.getPokemonPut().setHeight(10);
        environment.getPokemonPut().setHp(20);

        environment.getApi().getApiClient().addDefaultHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpZHVzZXIiOjEsImlzYWRtaW4iOnRydWUsImlhdCI6MTU3ODIzODM4Miwic3ViIjoiYWRtaW4iLCJleHAiOjE2MDk3OTUzMzR9.y98yisjtq6BDIOuJsiqP8d-RYPDg3sgbYYqIstfFuuw");
    }

    @When("^I add a new pokemon$")
    public void i_add_a_new_pokemon() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().createPokemonWithHttpInfo(environment.getPokemonPut()));

            environment.setPokemon((Pokemon) environment.getLastApiResponse().getData());

            environment.setLastApiException(null);
            environment.setLastApiCallThrewException(false);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiResponse(null);
            environment.setLastApiCallThrewException(true);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }

    @Then("^the system returns the added pokemon with (\\d+) status$")
    public void the_system_returns_the_added_pokemon_with_status(int arg1) throws Throwable {
        assertNotNull(environment.getPokemon());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^This new pokemon$")
    public void this_new_pokemon() throws Throwable {
        pokemonId = environment.getPokemon().getPokedexId();
    }

    @When("^I get information about this pokemon$")
    public void i_get_information_about_this_pokemon() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().getPokemonByIDWithHttpInfo(pokemonId));

            environment.setPokemon((Pokemon) environment.getLastApiResponse().getData());

            environment.setLastApiException(null);
            environment.setLastApiCallThrewException(false);
            environment.setLastStatusCode(environment.getLastApiResponse().getStatusCode());
        } catch (ApiException e) {
            environment.setLastApiResponse(null);
            environment.setLastApiCallThrewException(true);
            environment.setLastApiException(e);
            environment.setLastStatusCode(environment.getLastApiException().getCode());
        }
    }
}
