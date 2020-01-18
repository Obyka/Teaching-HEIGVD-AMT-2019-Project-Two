package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

public class OthersScenariosSteps {
    private Environment environment;

    public OthersScenariosSteps(Environment environment) {
        this.environment = environment;
    }

    @Given("^malformed data to insert$")
    public void malformed_data_to_insert() throws Throwable {
        environment.setPayloadJson("{\"value\":\"1\"}");
        environment.setLoginUrl("http://localhost:8080/api/pokemon/pokemons");

        environment.setHttpHeaders(new HttpHeaders());
        environment.getHttpHeaders().add("Authorization", environment.getAdminToken());
        environment.getHttpHeaders().add("Content-Type", "application/json");
        environment.getHttpHeaders().add("Accept", "application/json");

        environment.setEntity(new HttpEntity<String>(environment.getPayloadJson(), environment.getHttpHeaders()));
    }

    @When("^I try to insert a bad payload in pokemon$")
    public void i_try_to_insert_a_bad_payload_in_pokemon() throws Throwable {
        try {
            environment.setRestTemplate(new RestTemplate());
            environment.setResponse(environment.getRestTemplate().postForEntity(environment.getLoginUrl(), environment.getEntity(), String.class));

            environment.setLastStatusCode(environment.getResponse().getStatusCode().value());
        } catch (HttpClientErrorException e) {
            environment.setLastStatusCode(e.getStatusCode().value());
        }
    }

    @Then("^the system returns the (\\d+) status with an error$")
    public void the_system_returns_the_status_with_an_error(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

}
