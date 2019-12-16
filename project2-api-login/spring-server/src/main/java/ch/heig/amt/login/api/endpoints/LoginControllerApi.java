package ch.heig.amt.login.api.endpoints;

import ch.heig.amt.login.api.ApiUtil;
import ch.heig.amt.login.api.LoginApi;
import ch.heig.amt.login.api.model.Credentials;
import ch.heig.amt.login.api.model.ValidCreds;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class LoginControllerApi implements LoginApi {

    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    public ResponseEntity<ValidCreds> login(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Credentials credentials) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("*/*"))) {
                    String exampleString = "{ \"JWTToken\" : \"JWTToken\", \"userID\" : 0 }";
                    ApiUtil.setExampleResponse(request, "*/*", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }
}
