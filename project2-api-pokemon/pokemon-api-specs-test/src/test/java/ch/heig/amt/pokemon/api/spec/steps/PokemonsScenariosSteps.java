package ch.heig.amt.pokemon.api.spec.steps;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.dto.TrainerWithId;
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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PokemonsScenariosSteps {
    private Environment environment;
    private String lastMilliseconds;

    private String usernameNewUser;
    private ArrayList<Pokemon> pokemons;

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
        environment.setAdminToken((String) environment.getJsonObject().get("JWTToken"));

        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());
    }


    @Given("^a new pokemon to create$")
    public void a_new_pokemon_to_create() throws Throwable {
        environment.setLastMilliseconds("" + System.currentTimeMillis());
        environment.setLastMillisecondsInt(Math.abs((int) System.currentTimeMillis()));

        environment.getPokemonPut().setPokedexId(environment.getLastMillisecondsInt());
        environment.getPokemonPut().setName("Pokemon" + environment.getLastMillisecondsInt());
        environment.getPokemonPut().setCategory("Tester");
        environment.getPokemonPut().setType("Testing");
        environment.getPokemonPut().setHeight(10);
        environment.getPokemonPut().setHp(20);

        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());
    }

    @When("^I add a new pokemon$")
    public void i_add_a_new_pokemon() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

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
        environment.setPokemonId(environment.getPokemon().getPokedexId());
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

    @Given("^information for new user and other parameters$")
    public void information_for_new_user_and_other_parameters() throws Throwable {
        lastMilliseconds = "" + System.currentTimeMillis();

        environment.setPayloadJson("{\"password\":\"password\"," +
                "\"username\":\"user" + lastMilliseconds + "\"," +
                "\"mail\":\"" + lastMilliseconds + "@amt.com\"," +
                "\"firstname\":\"User" + lastMilliseconds + "\"," +
                "\"lastname\":\"" + lastMilliseconds + "\"," +
                "\"isadmin\": false}");

        environment.setLoginUrl("http://localhost:8090/api/login/users");

        environment.setHttpHeaders(new HttpHeaders());
        environment.getHttpHeaders().add("Content-Type", "application/json");
        environment.getHttpHeaders().add("Accept", "application/json");
        environment.getHttpHeaders().add("Authorization", environment.getAdminToken());

        environment.setEntity(new HttpEntity<String>(environment.getPayloadJson(), environment.getHttpHeaders()));
    }

    @When("^I insert this new user$")
    public void i_insert_this_new_user() throws Throwable {
        environment.setRestTemplate(new RestTemplate());
        environment.setResponse(environment.getRestTemplate().postForEntity(environment.getLoginUrl(), environment.getEntity(), String.class));
    }

    @Then("^The user has been created$")
    public void the_user_has_been_created() throws Throwable {
        environment.setResponsePostLogin(environment.getResponse().getBody().toString());

        environment.setJsonObject(new JSONObject(environment.getResponsePostLogin()));
        usernameNewUser = (String) environment.getJsonObject().get("username");
    }

    @Given("^credentials for new user$")
    public void credentials_for_new_user() throws Throwable {
        environment.setPayloadJson("{\"username\":\"" + usernameNewUser + "\",\"password\":\"password\"}");
        environment.setLoginUrl("http://localhost:8090/api/login/login");
    }

    @When("^I try to get the pokemon with this new user$")
    public void i_try_to_get_the_pokemon_with_this_new_user() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getPokemonByIDWithHttpInfo(environment.getPokemon().getPokedexId()));

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

    @Then("^The system returns me an error with (\\d+) status code$")
    public void the_system_returns_me_an_error_with_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^random pokeDexId$")
    public void random_pokeDexId() throws Throwable {
        environment.setPokemonId(0);
    }

    @When("^I get all pokemons$")
    public void i_get_all_pokemons() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getPokemonsWithHttpInfo(0, 20));

            pokemons = (ArrayList<Pokemon>) environment.getLastApiResponse().getData();

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

    @Then("^I receive all my pokemons belong to me and (\\d+) status code$")
    public void i_receive_all_my_pokemons_belong_to_me_and_status_code(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @When("^I delete this pokemon$")
    public void i_delete_this_pokemon() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().deletePokemonByIDWithHttpInfo(environment.getPokemonId()));

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

    @Then("^the system returns the (\\d+) status$")
    public void the_system_returns_the_status(int arg1) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
    }

    @Given("^modification on Pokemon$")
    public void modification_on_Pokemon() throws Throwable {
        environment.getPokemonPut().setPokedexId(environment.getPokemon().getPokedexId());
        environment.getPokemonPut().setName(environment.getPokemon().getName());
        environment.getPokemonPut().setCategory("Fire");
        environment.getPokemonPut().setType(environment.getPokemon().getType());
        environment.getPokemonPut().setHeight(environment.getPokemon().getHeight());
        environment.getPokemonPut().setHp(environment.getPokemon().getHp());
    }

    @When("^I update a pokemon$")
    public void i_update_a_pokemon() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().updatePokemonByIDWithHttpInfo(environment.getPokemon().getPokedexId(), environment.getPokemonPut()));

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

    @When("^I add (\\d+) pokemons$")
    public void i_add_pokemons(int arg1) throws Throwable {
        environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

        for (int i = 0; i < arg1; ++i) {
            environment.setLastMilliseconds("" + System.currentTimeMillis());
            environment.setLastMillisecondsInt(Math.abs((int) System.currentTimeMillis()));

            environment.getPokemonPut().setPokedexId(environment.getLastMillisecondsInt());
            environment.getPokemonPut().setName("Pokemon" + environment.getLastMillisecondsInt());
            environment.getPokemonPut().setCategory("Tester");
            environment.getPokemonPut().setType("Testing");
            environment.getPokemonPut().setHeight(10);
            environment.getPokemonPut().setHp(20);

            try {
                environment.setLastApiResponse(environment.getApi().createPokemonWithHttpInfo(environment.getPokemonPut()));

                environment.setPokemon((Pokemon)environment.getLastApiResponse().getData());

                environment.getAddedPokemons().add(environment.getPokemon());

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

    @When("^I delete all pokemons$")
    public void i_delete_all_pokemons() throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().deletePokemonsWithHttpInfo());

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

    @Then("^the system returns the (\\d+) status and size (\\d+) with pokemons$")
    public void the_system_returns_the_status_and_size_with_pokemons(int arg1, int arg2) throws Throwable {
        assertEquals(arg1, environment.getLastStatusCode());
        assertEquals(arg2, pokemons.size());
    }

    @When("^I get pokemons at specific page (\\d+) with specific size (\\d+)$")
    public void i_get_pokemons_at_specific_page_with_specific_size(int arg1, int arg2) throws Throwable {
        try {
            environment.getApi().getApiClient().addDefaultHeader("Authorization", environment.getAdminToken());

            environment.setLastApiResponse(environment.getApi().getPokemonsWithHttpInfo(arg1, arg2));

            pokemons = (ArrayList<Pokemon>) environment.getLastApiResponse().getData();

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
