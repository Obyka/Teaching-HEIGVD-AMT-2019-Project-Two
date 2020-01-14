package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.ApiResponse;
import ch.heig.amt.login.api.DefaultApi;
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
    private Credentials credentials;
    private ValidCreds validCreds;
    private String authorizationToken;
    private UserToPost userToPost;
    private UserToGet userToGet;
    private QueryPasswordChange queryPasswordChange;


    public CRUDoperationsSteps(Environment environment) throws IOException {
        this.environment = environment;

        credentials = new Credentials();
        validCreds = new ValidCreds();

        userToPost = new UserToPost();
        userToGet = new UserToGet();

        queryPasswordChange = new QueryPasswordChange();
    }

    @Given("^the credentials for administrator$")
    public void the_credentials_for_administrator() throws Throwable {
        credentials.setUsername("admin");
        credentials.setPassword("password");
    }

    @When("^I try to login in the system$")
    public void i_try_to_login_in_the_system() throws Throwable {
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

    @Then("^the system returns me a token with my identifior and a (\\d+) status code$")
    public void the_system_returns_me_a_token_with_my_identifior_and_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(validCreds);
    }

    @Given("^a new user to add and a valid administrator token$")
    public void a_new_user_to_add_and_a_valid_administrator_token() throws Throwable {
        authorizationToken = validCreds.getJwTToken();

        userToPost.setUsername("james");
        userToPost.setPassword("mister1511");
        userToPost.setMail("james.bond@mi6.com");
        userToPost.setIsadmin(false);
        userToPost.setLastname("Bond");
        userToPost.setFirstname("James");
    }

    @When("^I insert a this new user in the database$")
    public void i_insert_a_this_new_user_in_the_database() throws Throwable {
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

    @Then("^I received with the new user who have just insered and a (\\d+) status code$")
    public void i_received_with_the_new_user_who_have_just_insered_and_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(userToGet);
    }

    @Given("^valid token$")
    public void valid_token() throws Throwable {
        authorizationToken = validCreds.getJwTToken();
    }

    @When("^I search some information about me$")
    public void i_search_some_information_about_me() throws Throwable {
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

    @Then("^I received all information about me and (\\d+) status code$")
    public void i_received_all_information_about_me_and_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertNotNull(userToGet);
    }

    @Given("^credentials for normal user$")
    public void credentials_for_normal_user() throws Throwable {
        credentials.setUsername("james");
        credentials.setPassword("mister1511");
    }

    @Given("^new password$")
    public void new_password() throws Throwable {
        queryPasswordChange.setCurrentPassword("mister1511");
        queryPasswordChange.setNewPassword("pass");
    }

    @When("^I try to login in the system as normal user$")
    public void i_try_to_login_in_the_system_as_normal_user() throws Throwable {
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

    @When("^I update my password with a new password$")
    public void i_update_my_password_with_a_new_password() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().changePasswordWithHttpInfo(validCreds.getJwTToken(), queryPasswordChange));

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

    @Then("^I receive all my information and (\\d+) status code$")
    public void i_receive_all_my_information_and_status_code(int arg1) throws Throwable {
        assertNotNull(userToGet);
        assertEquals(arg1, environment.getLastStatusCode());
    }

}
