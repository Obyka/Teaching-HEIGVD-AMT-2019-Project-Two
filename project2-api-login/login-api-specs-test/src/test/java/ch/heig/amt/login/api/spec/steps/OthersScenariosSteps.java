package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.api.dto.Credentials;
import ch.heig.amt.login.api.dto.UserToGet;
import ch.heig.amt.login.api.dto.ValidCreds;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class OthersScenariosSteps {
    private Environment environment;
    private Collection collection;
    private Set<Map.Entry> entries;

    public OthersScenariosSteps(Environment environment) {
        this.environment = environment;
    }

    @Given("^malformed token$")
    public void malformed_token() throws Throwable {
        environment.setAuthorizationToken("blablabla");
    }

    @When("^I try to get information about me$")
    public void i_try_to_get_information_about_me() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().getUserWithHttpInfo(environment.getAuthorizationToken()));

            environment.setUserToGet((UserToGet) environment.getLastApiResponse().getData());

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

    @Then("^the system returns me a payload that is a valid payload$")
    public void the_system_returns_me_a_payload_that_is_a_valid_payload() throws Throwable {
        collection = environment.getLastApiResponse().getHeaders().values();
        ArrayList array = (ArrayList) collection.iterator().next();

        assertEquals("application/json", (String) array.get(0));
    }
}
