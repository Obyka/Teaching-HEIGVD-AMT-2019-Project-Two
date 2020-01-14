package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.api.dto.*;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ErrorStatusCodeSteps {
    private Environment environment;

    public ErrorStatusCodeSteps(Environment environment) {
        this.environment = environment;

        environment.credentials = new Credentials();
        environment.validCreds = new ValidCreds();
        environment.userToGet = new UserToGet();
        environment.userToPost = new UserToPost();
        environment.queryPasswordChange = new QueryPasswordChange();
    }

    @Given("^bad credentials$")
    public void bad_credentials() throws Throwable {
        environment.credentials.setPassword("pass");
        environment.credentials.setUsername("admin");
    }

    @When("^I try to connect with bad credentials$")
    public void i_try_to_connect_with_bad_credentials() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().loginWithHttpInfo(environment.credentials));

            environment.validCreds = (ValidCreds) environment.getLastApiResponse().getData();

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
        assertNull(environment.validCreds.getJwTToken());
        assertNull(environment.validCreds.getUserID());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^empty token$")
    public void empty_token() throws Throwable {
        environment.setAuthorizationToken("");
    }

    @When("^I try to get information with empty token$")
    public void i_try_to_get_information_with_empty_token() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().getUserWithHttpInfo(environment.getAuthorizationToken()));

            environment.userToGet = (UserToGet) environment.getLastApiResponse().getData();

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

    @Then("^the system returns me an error with (\\d+) status code$")
    public void the_system_returns_me_an_error_with_status_code(int arg1) throws Throwable {
        assertNull(environment.userToGet.getFirstname());
        assertNull(environment.userToGet.getId());
        assertNull(environment.userToGet.getLastname());
        assertNull(environment.userToGet.getIsadmin());
        assertNull(environment.userToGet.getMail());
        assertNull(environment.userToGet.getUsername());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^user to add$")
    public void user_to_add() throws Throwable {
        environment.userToPost.setUsername("jane");
        environment.userToPost.setPassword("mister1511");
        environment.userToPost.setMail("jane.money@mi6.com");
        environment.userToPost.setIsadmin(false);
        environment.userToPost.setLastname("Money");
        environment.userToPost.setFirstname("Jane");
    }


    @When("^I try to add a new user without token$")
    public void i_try_to_add_a_new_user_without_token() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().createAccountWithHttpInfo(environment.getAuthorizationToken(), environment.userToPost));

            environment.userToGet = (UserToGet) environment.getLastApiResponse().getData();

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

    @Then("^the system returns me an error that I have denied access with (\\d+) status code$")
    public void the_system_returns_me_an_error_that_I_have_denied_access_with_status_code(int arg1) throws Throwable {
        assertNull(environment.userToGet.getFirstname());
        assertNull(environment.userToGet.getId());
        assertNull(environment.userToGet.getLastname());
        assertNull(environment.userToGet.getIsadmin());
        assertNull(environment.userToGet.getMail());
        assertNull(environment.userToGet.getUsername());
        assertEquals(arg1, environment.getLastStatusCode());
    }
}
