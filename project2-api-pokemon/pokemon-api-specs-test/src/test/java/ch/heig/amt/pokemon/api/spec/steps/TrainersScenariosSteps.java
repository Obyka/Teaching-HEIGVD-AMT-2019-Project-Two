package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.dto.Trainer;
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

public class TrainersScenariosSteps {
    private Environment environment;
    private ArrayList<TrainerWithId> trainers;

    public TrainersScenariosSteps(Environment environment) {
        this.environment = environment;
    }

    @Given("^a new trainer$")
    public void a_new_trainer() throws Throwable {
        environment.setLastMilliseconds("" + System.currentTimeMillis());
        environment.setLastMillisecondsInt(Math.abs((int)System.currentTimeMillis()));

        environment.getTrainerPut().setName("Trainer"+environment.getLastMilliseconds());
        environment.getTrainerPut().setSurname(environment.getLastMilliseconds());
        environment.getTrainerPut().setGender("Male");
        environment.getTrainerPut().setAge(23);
        environment.getTrainerPut().setNumberOfBadges(10);

        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());
    }

    @When("^I add a new trainer$")
    public void i_add_a_new_trainer() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().createTrainerWithHttpInfo(environment.getTrainerPut()));

            environment.setTrainerWithId((TrainerWithId) environment.getLastApiResponse().getData());

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

    @When("^I get information about this trainer$")
    public void i_get_information_about_this_trainer() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getTrainerByIdWithHttpInfo(environment.getTrainerWithId().getId()));

            environment.setTrainerWithId((TrainerWithId) environment.getLastApiResponse().getData());

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

    @Then("^the system returns the added trainer with (\\d+) status$")
    public void the_system_returns_the_added_trainer_with_status(int arg1) throws Throwable {
        assertNotNull(environment.getTrainerWithId());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^random trainer ID$")
    public void random_trainer_ID() throws Throwable {
        environment.getTrainerWithId().setId(0);
    }

    @When("^I get all trainers$")
    public void i_get_all_trainers() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getTrainersWithHttpInfo(0, 20));

            trainers = (ArrayList<TrainerWithId>)environment.getLastApiResponse().getData();

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

    @Then("^I receive all my trainers belong to me and (\\d+) status code$")
    public void i_receive_all_my_trainers_belong_to_me_and_status_code(int arg1) throws Throwable {
        assertNotNull(trainers);
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @When("^I delete this trainer$")
    public void i_delete_this_trainer() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().deleteTrainerByIdWithHttpInfo(environment.getTrainerWithId().getId()));

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

    @Given("^modification on trainer$")
    public void modification_on_trainer() throws Throwable {
        environment.getTrainerPut().setName(environment.getTrainerWithId().getName());
        environment.getTrainerPut().setSurname("Diamond");
        environment.getTrainerPut().setGender("Male");
        environment.getTrainerPut().setAge(23);
        environment.getTrainerPut().setNumberOfBadges(10);
    }

    @When("^I update a trainer$")
    public void i_update_a_trainer() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().updateTrainerByIdWithHttpInfo(environment.getTrainerWithId().getId(), environment.getTrainerPut()));

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

    @When("^I get trainers at specific page (\\d+) with specific size (\\d+)$")
    public void i_get_trainers_at_specific_page_with_specific_size(int arg1, int arg2) throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getTrainersWithHttpInfo(arg1, arg2));

            trainers = (ArrayList<TrainerWithId>)environment.getLastApiResponse().getData();

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

    @Then("^the system returns the (\\d+) status and size (\\d+) with trainers$")
    public void the_system_returns_the_status_and_size_with_trainers(int arg1, int arg2) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertEquals(arg2, trainers.size());
    }

    @Then("^The system returns me an error with (\\d+) status code with trainers$")
    public void the_system_returns_me_an_error_with_status_code_with_trainers(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @When("^I delete all trainers$")
    public void i_delete_all_trainers() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().deleteTrainersWithHttpInfo());

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

    @When("^I create (\\d+) trainers$")
    public void i_create_trainers(int arg1) throws Throwable {
        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

        for(int i = 0; i < arg1; ++i) {
            environment.setLastMilliseconds("" + System.currentTimeMillis());
            environment.setLastMillisecondsInt(Math.abs((int)System.currentTimeMillis()));

            environment.getTrainerPut().setName("Trainer"+environment.getLastMilliseconds());
            environment.getTrainerPut().setSurname(environment.getLastMilliseconds());
            environment.getTrainerPut().setGender("Male");
            environment.getTrainerPut().setAge(23);
            environment.getTrainerPut().setNumberOfBadges(10);

            try {
                environment.setLastApiResponse(environment.getApi().createTrainerWithHttpInfo(environment.getTrainerPut()));

                environment.setTrainerWithId((TrainerWithId) environment.getLastApiResponse().getData());

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

}
