package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.dto.TrainerWithId;
import ch.heig.amt.pokemon.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainersScenariosSteps {
    private Environment environment;

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

        environment.getApi().getApiClient().addDefaultHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpZHVzZXIiOjEsImlzYWRtaW4iOnRydWUsImlhdCI6MTU3ODIzODM4Miwic3ViIjoiYWRtaW4iLCJleHAiOjE2MDk3OTUzMzR9.y98yisjtq6BDIOuJsiqP8d-RYPDg3sgbYYqIstfFuuw");
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

}
