package ch.heig.amt.pokemon.api.spec.helpers;

import ch.heig.amt.pokemon.ApiException;
import ch.heig.amt.pokemon.ApiResponse;
import ch.heig.amt.pokemon.api.DefaultApi;
import ch.heig.amt.pokemon.api.dto.Pokemon;
import ch.heig.amt.pokemon.api.dto.PokemonPut;
import lombok.Getter;
import lombok.Setter;

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
