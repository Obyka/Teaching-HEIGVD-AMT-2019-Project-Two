package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.ApiResponse;
import ch.heig.amt.login.api.DefaultApi;
import ch.heig.amt.login.api.dto.Credentials;
import ch.heig.amt.login.api.dto.UserToGet;
import ch.heig.amt.login.api.dto.UserToPost;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.swagger.annotations.Api;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CRUDoperationsSteps {
    private Environment environment;
    private DefaultApi api;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    Credentials credentials;
    UserToGet userToGet;
    UserToPost userToPost;
    String authorization;

    public CRUDoperationsSteps(Environment environment) {
        this.environment = environment;
        this.api = environment.getApi();
    }

    @Given("^there is a Login server$")
    public void there_is_a_Login_server() throws Throwable {
        assertNotNull(api);
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heig.amt.login.server.url");
        api.getApiClient().setBasePath(url);
    }

    @Given("^UserToPost payload$")
    public void usertopost_payload() throws Throwable {
        userToGet = new UserToGet();
        userToPost = new UserToPost();
    }

    @When("^I POST a login to /users$")
    public void i_POST_a_login_to_users() throws Throwable {
        try {
            lastApiResponse = api.createAccountWithHttpInfo("eyJhbGciOiJIUzI1NiJ9.eyJpZHVzZXIiOjEsImlzYWRtaW4iOnRydWUsImlhdCI6MTU3ODg1MjE5Nywic3ViIjoiYWRtaW4iLCJleHAiOjE2MTA0MDkxNDl9.2HSzLOtypaL76Cd4mzFYAlzglaXM3HwPxAHcFPYID6E", userToPost);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Given("^authorization string$")
    public void authorization_string() throws Throwable {
        authorization = "eyJhbGciOiJIUzI1NiJ9.eyJpZHVzZXIiOjEsImlzYWRtaW4iOnRydWUsImlhdCI6MTU3ODg1MjE5Nywic3ViIjoiYWRtaW4iLCJleHAiOjE2MTA0MDkxNDl9.2HSzLOtypaL76Cd4mzFYAlzglaXM3HwPxAHcFPYID6E";
    }

    @When("^I GET with authorization string$")
    public void i_GET_with_authorization_string() throws Throwable {
        try {
            lastApiResponse = api.getUserWithHttpInfo(authorization);
            lastApiCallThrewException = false;
            lastApiException = null;
            lastStatusCode = lastApiResponse.getStatusCode();
        } catch (ApiException e) {
            lastApiCallThrewException = true;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }


    @Then("^I received a (\\d+) code status with UserToGet payload$")
    public void i_received_a_code_status_with_UserToGet_payload(int arg1) throws Throwable {
        assertEquals(201, arg1);
    }

    @Given("^Credentials$")
    public void credentials() throws Throwable {
        credentials = new Credentials();
    }

    @When("^I POST with Credentials to /login$")
    public void i_POST_with_Credentials_to_login() throws Throwable {
        try {
            lastApiResponse = api.loginWithHttpInfo(credentials);
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

    @Then("^I received a (\\d+) code status with ValidCreds payload$")
    public void i_received_a_code_status_with_ValidCreds_payload(int arg1) throws Throwable {
        assertEquals(201, arg1);
    }

}
