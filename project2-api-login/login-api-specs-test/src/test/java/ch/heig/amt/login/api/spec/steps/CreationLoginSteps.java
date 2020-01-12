package ch.heig.amt.login.api.spec.steps;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.ApiResponse;
import ch.heig.amt.login.api.DefaultApi;
import ch.heig.amt.login.api.dto.Credentials;
import ch.heig.amt.login.api.dto.UserToGet;
import ch.heig.amt.login.api.dto.UserToPost;
import ch.heig.amt.login.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Olivier Liechti on 27/07/17.
 */
public class CreationLoginSteps {
    private Environment environment;
    private DefaultApi api;
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;
    Credentials credentials;
    UserToGet userToGet;
    UserToPost userToPost;

    public CreationLoginSteps(Environment environment) {
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
        credentials = new Credentials();
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
            lastApiCallThrewException = false;
            lastApiResponse = null;
            lastApiException = e;
            lastStatusCode = lastApiException.getCode();
        }
    }

    @Then("^I received a (\\d+) code status with UserToGet payload$")
    public void i_received_a_code_status_with_UserToGet_payload(int arg1) throws Throwable {
        assertEquals(201, arg1);
    }
}
