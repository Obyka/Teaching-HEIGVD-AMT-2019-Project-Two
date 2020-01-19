package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.dto.CaptureGet;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.dto.TrainerWithId;
import ch.heig.amt.pokemon.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CapturesScenariosSteps {
    private Environment environment;
    private ArrayList<CaptureGet> captures;

    public CapturesScenariosSteps(Environment environment) {
        this.environment = environment;
    }

    @Then("^I get captures about a trainer at specific page (\\d+) with specific size (\\d+)$")
    public void i_get_captures_about_a_trainer_at_specific_page_with_specific_size(int arg1, int arg2) throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getTrainerWithPokemonsWithHttpInfo(environment.getTrainerWithId().getId(), arg1, arg2));

            captures = (ArrayList<CaptureGet>)environment.getLastApiResponse().getData();

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

    @Then("^the system returns the (\\d+) status and size (\\d+) with captures$")
    public void the_system_returns_the_status_and_size_with_captures(int arg1, int arg2) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^a new capture$")
    public void a_new_capture() throws Throwable {
        environment.getCapturePost().setDateCapture("2019-01-17 23:45:56");
        environment.getCapturePost().setIdTrainer(environment.getTrainerWithId().getId());
        environment.getCapturePost().setIdPokemon(environment.getPokemon().getPokedexId());
    }

    @When("^I create a new capture$")
    public void i_create_a_new_capture() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().createCaptureWithHttpInfo(environment.getCapturePost()));

            environment.setCaptureGet((CaptureGet)environment.getLastApiResponse().getData());

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

    @Then("^the system returns the added capture with (\\d+) status$")
    public void the_system_returns_the_added_capture_with_status(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(environment.getCaptureGet());
    }

    @When("^I get information about this capture with the new pokemon$")
    public void i_get_information_about_this_capture_with_the_new_pokemon() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getPokemonWithTrainersWithHttpInfo(environment.getPokemon().getPokedexId(), 0, 1));

            captures = (ArrayList<CaptureGet>)environment.getLastApiResponse().getData();

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

    @Then("^the system returns the added capture with pokemon with (\\d+) status$")
    public void the_system_returns_the_added_capture_with_pokemon_with_status(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(captures);
    }

    @When("^I get information about this capture with the new trainer$")
    public void i_get_information_about_this_capture_with_the_new_trainer() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getTrainerWithPokemonsWithHttpInfo(environment.getTrainerWithId().getId(), 0, 1));

            captures = (ArrayList<CaptureGet>)environment.getLastApiResponse().getData();

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

    @Then("^the system returns the added capture with trainer with (\\d+) status$")
    public void the_system_returns_the_added_capture_with_trainer_with_status(int arg1) throws Throwable {
        assertNotNull(captures);
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @When("^I get information about captures with a unexisted pokemon$")
    public void i_get_information_about_captures_with_a_unexisted_pokemon() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getPokemonWithTrainersWithHttpInfo(environment.getPokemonId(), 0, 1));

            environment.setCaptureGet((CaptureGet)environment.getLastApiResponse().getData());

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

    @When("^I get information about captures with a unexisted trainer$")
    public void i_get_information_about_captures_with_a_unexisted_trainer() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getTrainerWithPokemonsWithHttpInfo(environment.getTrainerWithId().getId(), 0, 1));

            environment.setCaptureGet((CaptureGet)environment.getLastApiResponse().getData());

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

    @When("^I add (\\d+) captures about a trainer$")
    public void i_add_captures_about_a_trainer(int arg1) throws Throwable {
        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());
        environment.getCapturePost().setIdTrainer(environment.getTrainerWithId().getId());

        for(int i = 0; i < arg1; ++i) {
            environment.setLastMilliseconds("" + System.currentTimeMillis());
            environment.setLastMillisecondsInt(Math.abs((int)System.currentTimeMillis()));

            environment.getCapturePost().setIdPokemon(environment.getAddedPokemons().get(i).getPokedexId());
            environment.getCapturePost().setDateCapture("2019-01-18 02:55:45");

            try {
                environment.setLastApiResponse(environment.getApi().createCaptureWithHttpInfo(environment.getCapturePost()));

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

    @When("^I add (\\d+) captures about a pokemon$")
    public void i_add_captures_about_a_pokemon(int arg1) throws Throwable {
        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());
        environment.getCapturePost().setIdPokemon(environment.getPokemon().getPokedexId());

        for(int i = 0; i < arg1; ++i) {
            environment.setLastMilliseconds("" + System.currentTimeMillis());
            environment.setLastMillisecondsInt(Math.abs((int)System.currentTimeMillis()));

            environment.getCapturePost().setIdTrainer(environment.getAddedTrainers().get(i).getId());
            environment.getCapturePost().setDateCapture("2020-02-02 02:55:45");

            try {
                environment.setLastApiResponse(environment.getApi().createCaptureWithHttpInfo(environment.getCapturePost()));

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

    @When("^I get captures about a pokemon at specific page (\\d+) with specific size (\\d+)$")
    public void i_get_captures_about_a_pokemon_at_specific_page_with_specific_size(int arg1, int arg2) throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getPokemonWithTrainersWithHttpInfo(environment.getCapturePost().getIdPokemon(), arg1, arg2));

            captures = (ArrayList<CaptureGet>)environment.getLastApiResponse().getData();

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
