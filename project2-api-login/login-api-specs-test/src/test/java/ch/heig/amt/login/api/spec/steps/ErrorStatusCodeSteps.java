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
    }

    @Given("^bad credentials$")
    public void bad_credentials() throws Throwable {
        environment.getCredentials().setPassword("pass");
        environment.getCredentials().setUsername("admin");
    }

    @When("^I try to connect with bad credentials$")
    public void i_try_to_connect_with_bad_credentials() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().loginWithHttpInfo(environment.getCredentials()));

            environment.setValidCreds((ValidCreds) environment.getLastApiResponse().getData());

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
        assertNull(environment.getValidCreds().getJwTToken());
        assertNull(environment.getValidCreds().getUserID());
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

    @Then("^the system returns me an error with (\\d+) status code$")
    public void the_system_returns_me_an_error_with_status_code(int arg1) throws Throwable {
        assertNull(environment.getUserToGet().getFirstname());
        assertNull(environment.getUserToGet().getId());
        assertNull(environment.getUserToGet().getLastname());
        assertNull(environment.getUserToGet().getIsadmin());
        assertNull(environment.getUserToGet().getMail());
        assertNull(environment.getUserToGet().getUsername());

        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^user to add$")
    public void user_to_add() throws Throwable {
        environment.setLastMilliSeconds("" + System.currentTimeMillis());

        environment.getUserToPost().setUsername("user" + environment.getLastMilliSeconds());
        environment.getUserToPost().setPassword("password");
        environment.getUserToPost().setMail(environment.getLastMilliSeconds() + "@amt.com");
        environment.getUserToPost().setIsadmin(false);
        environment.getUserToPost().setLastname(environment.getLastMilliSeconds());
        environment.getUserToPost().setFirstname("User" + environment.getLastMilliSeconds());
    }


    @When("^I try to add a new user without token$")
    public void i_try_to_add_a_new_user_without_token() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().createAccountWithHttpInfo(environment.getAuthorizationToken(), environment.getUserToPost()));

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

    @Then("^the system returns me an error that I have denied access with (\\d+) status code$")
    public void the_system_returns_me_an_error_that_I_have_denied_access_with_status_code(int arg1) throws Throwable {
        assertNull(environment.getUserToGet().getFirstname());
        assertNull(environment.getUserToGet().getId());
        assertNull(environment.getUserToGet().getLastname());
        assertNull(environment.getUserToGet().getIsadmin());
        assertNull(environment.getUserToGet().getMail());
        assertNull(environment.getUserToGet().getUsername());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @When("^I try to add a new user with normal user token$")
    public void i_try_to_add_a_new_user_with_normal_user_token() throws Throwable {
        try {
            // before null, because if the next line leave an exception, the UserToGet has always the
            //value of the previous added user
            environment.setUserToGet(new UserToGet());

            environment.setLastApiResponse(environment.getApi().createAccountWithHttpInfo(environment.getAuthorizationToken(), environment.getUserToPost()));

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

    @Given("^valid token for normal added user$")
    public void valid_token_for_normal_added_user() throws Throwable {
        environment.setAuthorizationToken(environment.getValidCreds().getJwTToken());
    }

}
