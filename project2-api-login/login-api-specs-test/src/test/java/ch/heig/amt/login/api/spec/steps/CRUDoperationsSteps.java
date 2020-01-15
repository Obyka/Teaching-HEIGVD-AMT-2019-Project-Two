package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.api.dto.*;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CRUDoperationsSteps {
    private Environment environment;

    public CRUDoperationsSteps(Environment environment) throws IOException {
        this.environment = environment;
    }

    @Given("^the credentials for administrator$")
    public void the_credentials_for_administrator() throws Throwable {
        environment.getCredentials().setUsername("admin");
        environment.getCredentials().setPassword("password");
    }

    @When("^I try to login in the system$")
    public void i_try_to_login_in_the_system() throws Throwable {
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

    @Then("^the system returns me a token with my identifior and a (\\d+) status code$")
    public void the_system_returns_me_a_token_with_my_identifior_and_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(environment.getValidCreds());
    }

    @Given("^valid token$")
    public void valid_token() throws Throwable {
        environment.setAuthorizationToken(environment.getValidCreds().getJwTToken());
    }

    @When("^I search some information about me$")
    public void i_search_some_information_about_me() throws Throwable {
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

    @Then("^I received all information about me and (\\d+) status code$")
    public void i_received_all_information_about_me_and_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(environment.getUserToGet());
    }

    @Given("^a new user to add$")
    public void a_new_user_to_add() throws Throwable {
        environment.setLastMilliSeconds("" + System.currentTimeMillis());

        environment.getUserToPost().setUsername("user" + environment.getLastMilliSeconds());
        environment.getUserToPost().setPassword("password");
        environment.getUserToPost().setMail(environment.getLastMilliSeconds() + "@amt.com");
        environment.getUserToPost().setIsadmin(false);
        environment.getUserToPost().setLastname(environment.getLastMilliSeconds());
        environment.getUserToPost().setFirstname("User" + environment.getLastMilliSeconds());
    }

    @Given("^a valid administrator token$")
    public void a_valid_administrator_token() throws Throwable {
        environment.setAuthorizationToken(environment.getValidCreds().getJwTToken());
    }

    @When("^I insert a this new user in the database$")
    public void i_insert_a_this_new_user_in_the_database() throws Throwable {
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

    @Then("^I received with the new user who have just insered and a (\\d+) status code$")
    public void i_received_with_the_new_user_who_have_just_insered_and_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(environment.getUserToGet());
    }

    @Given("^credentials for normal user$")
    public void credentials_for_normal_user() throws Throwable {
        environment.getCredentials().setUsername(environment.getUserToGet().getUsername());
        environment.getCredentials().setPassword("password");
    }

    @When("^I try to login in the system as normal user$")
    public void i_try_to_login_in_the_system_as_normal_user() throws Throwable {
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

    @Then("^I received a valid token with (\\d+) status code$")
    public void i_received_a_valid_token_with_status_code(int arg1) throws Throwable {
        assertNotNull(environment.getValidCreds().getJwTToken());
        assertEquals(arg1, environment.getLastStatusCode());
        assertEquals(environment.getUserToGet().getId(), environment.getValidCreds().getUserID());
    }

    @Given("^new password$")
    public void new_password() throws Throwable {
        environment.getQueryPasswordChange().setCurrentPassword("password");
        environment.getQueryPasswordChange().setNewPassword("pass");
    }

    @When("^I update my password with a new password$")
    public void i_update_my_password_with_a_new_password() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().changePasswordWithHttpInfo(environment.getValidCreds().getJwTToken(), environment.getQueryPasswordChange()));

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

    @Then("^I receive all my information and (\\d+) status code$")
    public void i_receive_all_my_information_and_status_code(int arg1) throws Throwable {
        assertNotNull(environment.getUserToGet());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^valid token for normal user$")
    public void valid_token_for_normal_user() throws Throwable {
        environment.setAuthorizationToken(environment.getValidCreds().getJwTToken());
    }
}
