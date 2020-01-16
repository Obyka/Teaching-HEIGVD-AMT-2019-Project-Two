package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.spec.helpers.Environment;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PokemonsScenariosSteps {
    private Environment environment;

    public PokemonsScenariosSteps(Environment environment) {
        this.environment = environment;
    }

    @Given("^payload JSON and HTTP data and header$")
    public void payload_JSON_and_HTTP_data_and_header() throws Throwable {
        environment.setPayloadJson("{\"username\":\"admin\",\"password\":\"password\"}");
        environment.setLoginUrl("http://localhost:8090/api/login/login");

        environment.setHttpHeaders(new HttpHeaders());
        environment.getHttpHeaders().add("Content-Type", "application/json");
        environment.getHttpHeaders().add("Accept", "application/json");

        environment.setEntity(new HttpEntity<String>(environment.getPayloadJson(), environment.getHttpHeaders()));
    }

    @When("^I make a POST request to /api/login/login$")
    public void i_make_a_POST_request_to_api_login_login() throws Throwable {
        environment.setRestTemplate(new RestTemplate());
        environment.setResponse(environment.getRestTemplate().postForEntity(environment.getLoginUrl(), environment.getEntity(), String.class));
    }

    @Then("^I receive a valid token$")
    public void i_receive_a_valid_token() throws Throwable {
        environment.setResponsePostLogin(environment.getResponse().getBody().toString());

        environment.setJsonObject(new JSONObject(environment.getResponsePostLogin()));
        environment.setAdminToken((String)environment.getJsonObject().get("JWTToken"));
    }


    @Given("^a new pokemon to create$")
    public void a_new_pokemon_to_create() throws Throwable {
        environment.setLastMilliseconds("" + System.currentTimeMillis());
        environment.setLastMillisecondsInt(Math.abs((int)System.currentTimeMillis()));

        environment.getPokemonPut().setPokedexId(environment.getLastMillisecondsInt());
        environment.getPokemonPut().setName("Pokemon"+environment.getLastMillisecondsInt());
        environment.getPokemonPut().setCategory("Tester");
        environment.getPokemonPut().setType("Testing");
        environment.getPokemonPut().setHeight(10);
        environment.getPokemonPut().setHp(20);

        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());
    }

    @When("^I add a new pokemon$")
    public void i_add_a_new_pokemon() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().createPokemonWithHttpInfo(environment.getPokemonPut()));

            environment.setPokemon((Pokemon) environment.getLastApiResponse().getData());

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

    @Then("^the system returns the added pokemon with (\\d+) status$")
    public void the_system_returns_the_added_pokemon_with_status(int arg1) throws Throwable {
        assertNotNull(environment.getPokemon());
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^This new pokemon$")
    public void this_new_pokemon() throws Throwable {
        environment.setPokemonId(environment.getPokemon().getPokedexId());
    }

    @When("^I get information about this pokemon$")
    public void i_get_information_about_this_pokemon() throws Throwable {
        try {
            environment.setLastApiResponse(environment.getApi().getPokemonByIDWithHttpInfo(environment.getPokemonId()));

            environment.setPokemon((Pokemon) environment.getLastApiResponse().getData());

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

}
