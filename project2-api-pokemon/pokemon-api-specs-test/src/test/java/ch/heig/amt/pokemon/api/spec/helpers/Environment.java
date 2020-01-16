package ch.heig.amt.pokemon.api.spec.helpers;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.ApiResponse;
import ch.heig.amt.pokemon.api.DefaultApi;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.dto.PokemonPut;
import ch.heig.amt.pokemon.api.dto.TrainerPut;
import ch.heig.amt.pokemon.api.dto.TrainerWithId;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */

@Getter
@Setter

public class Environment {

    private DefaultApi api = new DefaultApi();
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    private PokemonPut pokemonPut = new PokemonPut();
    private Pokemon pokemon = new Pokemon();

    private TrainerPut trainerPut = new TrainerPut();
    private TrainerWithId trainerWithId = new TrainerWithId();

    private String lastMilliseconds;
    private int lastMillisecondsInt;

    private Integer pokemonId;
    private String payloadJson;
    private String loginUrl;
    private HttpHeaders httpHeaders;
    private HttpEntity<String> entity;
    private RestTemplate restTemplate;
    private ResponseEntity<String> response;

    private JSONObject jsonObject;

    private String responsePostLogin;
    private String adminToken;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heig.amt.pokemon.server.url");
        api.getApiClient().setBasePath(url);

    }

    public DefaultApi getApi() {
        return api;
    }


}
