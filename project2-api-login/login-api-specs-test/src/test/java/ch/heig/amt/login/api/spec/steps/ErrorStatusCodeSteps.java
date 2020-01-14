package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.api.dto.Credentials;
import ch.heig.amt.login.api.dto.ValidCreds;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ErrorStatusCodeSteps {
    private Environment environment;

    private Credentials credentials;
    private ValidCreds validCreds;

    public ErrorStatusCodeSteps(Environment environment) {
        this.environment = environment;

        credentials = new Credentials();
        validCreds = new ValidCreds();
    }

    @Given("^bad credentials$")
    public void bad_credentials() throws Throwable {
        credentials.setPassword("pass");
        credentials.setUsername("admin");
    }

    @When("^I try to connect with bad credentials$")
    public void i_try_to_connect_with_bad_credentials() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().loginWithHttpInfo(credentials));

            validCreds = (ValidCreds) environment.getLastApiResponse().getData();

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

    @Then("^the system returns me an error that it is a bad login with (\\d+) status code$")
    public void the_system_returns_me_an_error_that_it_is_a_bad_login_with_status_code(int arg1) throws Throwable {
        assertNull(validCreds.getJwTToken());
        assertNull(validCreds.getUserID());
        assertEquals(arg1, environment.getLastStatusCode());
    }

}
