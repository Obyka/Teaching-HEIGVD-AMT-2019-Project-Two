package ch.heig.amt.login.api.spec.helpers;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.ApiResponse;
import ch.heig.amt.login.api.DefaultApi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {
    private DefaultApi api = new DefaultApi();
    private ApiResponse lastApiResponse;
    private ApiException lastApiException;
    private boolean lastApiCallThrewException;
    private int lastStatusCode;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heig.amt.login.server.url");
        api.getApiClient().setBasePath(url);
    }

    public DefaultApi getApi() {
        return api;
    }

    public ApiResponse getLastApiResponse() { return lastApiResponse; }

    public ApiException getLastApiException() { return lastApiException; }

    public boolean getLastApiCallThrewException() { return lastApiCallThrewException; }

    public int getLastStatusCode() { return lastStatusCode; }


}
