package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.api.dto.Credentials;
import ch.heig.amt.login.api.dto.UserToGet;
import ch.heig.amt.login.api.dto.UserToPost;
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

    private String authorizationToken;
    private UserToGet userToGet;
    private UserToPost userToPost;

    public ErrorStatusCodeSteps(Environment environment) {
        this.environment = environment;

        credentials = new Credentials();
        validCreds = new ValidCreds();
        userToGet = new UserToGet();
        userToPost = new UserToPost();
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

    @Given("^empty token$")
    public void empty_token() throws Throwable {
        authorizationToken = "";
    }

    @When("^I try to get information with empty token$")
    public void i_try_to_get_information_with_empty_token() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().getUserWithHttpInfo(authorizationToken));

            userToGet = (UserToGet) environment.getLastApiResponse().getData();

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
        assertNull(userToGet.getFirstname());
        assertNull(userToGet.getId());
        assertNull(userToGet.getLastname());
        assertNull(userToGet.getIsadmin());
        assertNull(userToGet.getMail());
        assertNull(userToGet.getUsername());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^user to add$")
    public void user_to_add() throws Throwable {
        userToPost.setUsername("jane");
        userToPost.setPassword("mister1511");
        userToPost.setMail("jane.money@mi6.com");
        userToPost.setIsadmin(false);
        userToPost.setLastname("Money");
        userToPost.setFirstname("Jane");
    }


    @When("^I try to add a new user without token$")
    public void i_try_to_add_a_new_user_without_token() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().createAccountWithHttpInfo(authorizationToken, userToPost));

            userToGet = (UserToGet) environment.getLastApiResponse().getData();

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
        assertNull(userToGet.getFirstname());
        assertNull(userToGet.getId());
        assertNull(userToGet.getLastname());
        assertNull(userToGet.getIsadmin());
        assertNull(userToGet.getMail());
        assertNull(userToGet.getUsername());
        assertEquals(arg1, environment.getLastStatusCode());
    }


}
