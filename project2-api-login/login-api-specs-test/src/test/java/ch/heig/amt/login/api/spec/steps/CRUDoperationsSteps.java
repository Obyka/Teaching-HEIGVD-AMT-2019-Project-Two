package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.ApiResponse;
import ch.heig.amt.login.api.DefaultApi;
import ch.heig.amt.login.api.dto.Credentials;
import ch.heig.amt.login.api.dto.ValidCreds;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CRUDoperationsSteps {
    private Environment environment = new Environment();
    private DefaultApi api;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private Credentials credentials;
    private ValidCreds validCreds;

    public CRUDoperationsSteps(Environment environment) throws IOException {
        this.environment = environment;
        this.api = environment.getApi();
        this.lastApiResponse = environment.getLastApiResponse();
        this.lastApiException = environment.getLastApiException();
        this.lastApiCallThrewException = environment.getLastApiCallThrewException();
        this.lastStatusCode = environment.getLastStatusCode();
    }

    @Given("^there is a Login server$")
    public void there_is_a_Login_server() throws Throwable {
        assertNotNull(api);
    }

    @Given("^the credentials for administrator$")
    public void the_credentials_for_administrator() throws Throwable {
        credentials = new Credentials();

        credentials.setUsername("admin");
        credentials.setPassword("password");
    }

    @When("^I try to login in the system$")
    public void i_try_to_login_in_the_system() throws Throwable {
        try {
            lastApiResponse = api.loginWithHttpInfo(credentials);

            validCreds = (ValidCreds) lastApiResponse.getData();

            lastApiException = null;
            lastApiCallThrewException = false;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiResponse = null;
            lastApiCallThrewException = true;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Then("^the system returns me a token with my identifior and a (\\d+) status code$")
    public void the_system_returns_me_a_token_with_my_identifior_and_a_status_code(int arg1) throws Throwable {
        assertEquals(arg1, lastStatusCode);
        assertNotNull(validCreds);
    }


    @Given("^a new user to add$")
    public void a_new_user_to_add() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a valid administrator token$")
    public void a_valid_administrator_token() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I insert a this new user in the database$")
    public void i_insert_a_this_new_user_in_the_database() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I received with the new user who have just insered$")
    public void i_received_with_the_new_user_who_have_just_insered() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^valid token$")
    public void valid_token() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I search some information about me$")
    public void i_search_some_information_about_me() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I received all information about me$")
    public void i_received_all_information_about_me() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a valid token$")
    public void a_valid_token() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^new password$")
    public void new_password() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I update my password with a new password$")
    public void i_update_my_password_with_a_new_password() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I receive all about my information$")
    public void i_receive_all_about_my_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
