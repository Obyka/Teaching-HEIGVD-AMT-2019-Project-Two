package ch.heig.amt.login.api.spec.helpers;

import ch.heig.amt.login.ApiException;
import ch.heig.amt.login.ApiResponse;
import ch.heig.amt.login.api.DefaultApi;
import ch.heig.amt.login.api.dto.*;
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

    // PicoContainer : http://www.thinkcode.se/blog/2017/04/01/sharing-state-between-steps-in-cucumberjvm-using-picocontainer
    // And : https://medium.com/@Raghwendra.sonu/sharing-state-between-steps-in-cucumber-java-using-picocontainer-ad39d4c0309c
    private Credentials credentials = new Credentials();
    private ValidCreds validCreds = new ValidCreds();

    private String lastMilliSeconds;
    private String authorizationToken;
    private UserToGet userToGet = new UserToGet();
    private UserToPost userToPost = new UserToPost();
    private QueryPasswordChange queryPasswordChange = new QueryPasswordChange();

    private String payloadJson;
    private String loginUrl;
    private HttpHeaders httpHeaders;
    private HttpEntity<String> entity;
    private RestTemplate restTemplate;
    private ResponseEntity<String> response;

    private JSONObject jsonObject;

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heig.amt.login.server.url");
        api.getApiClient().setBasePath(url);
    }
}
