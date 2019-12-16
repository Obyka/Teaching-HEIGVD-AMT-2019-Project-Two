package ch.heig.amt.login.api.endpoints;

import ch.heig.amt.login.api.ApiUtil;
import ch.heig.amt.login.api.PasswordApi;
import ch.heig.amt.login.api.model.QueryPasswordChange;
import ch.heig.amt.login.api.model.UserToGet;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class PasswordControllerApi implements PasswordApi {

    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    public ResponseEntity<UserToGet> changePassword(@ApiParam(value = "" ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "" ,required=true )  @Valid @RequestBody QueryPasswordChange queryPassword) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("*/*"))) {
                    String exampleString = "null";
                    ApiUtil.setExampleResponse(request, "*/*", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
