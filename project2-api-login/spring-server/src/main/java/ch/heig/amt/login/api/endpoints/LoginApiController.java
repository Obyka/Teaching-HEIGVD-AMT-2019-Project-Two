package ch.heig.amt.login.api.endpoints;

import ch.heig.amt.login.api.LoginApi;
import ch.heig.amt.login.api.exceptions.BadLoginException;
import ch.heig.amt.login.api.model.Credentials;
import ch.heig.amt.login.api.model.ValidCreds;
import ch.heig.amt.login.api.util.PasswordHash;
import ch.heig.amt.login.api.util.UtilsJWT;
import ch.heig.amt.login.entities.UserEntity;
import ch.heig.amt.login.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginApiController implements LoginApi {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ValidCreds> login(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Credentials credentials) {
        PasswordHash ph = new PasswordHash();



        UserEntity fetchedUserEntity = userRepository.findByusername(credentials.getUsername());

        if(fetchedUserEntity == null) {
            throw new BadLoginException("Bad login");
        }

        ValidCreds validCreds = new ValidCreds();

        Boolean validPass;
        try{
            validPass = ph.validatePassword(credentials.getPassword(), fetchedUserEntity.getPassword());
        } catch (Exception e){
            throw new RuntimeException();
        }

        if(validPass){
            validCreds.setUserID(fetchedUserEntity.getId());
            validCreds.setJwTToken(UtilsJWT.createJWT(fetchedUserEntity.getUsername(),UtilsJWT.VALIDITY, fetchedUserEntity.getIsadmin(), fetchedUserEntity.getId()));
        } else{
            throw new BadLoginException("Bad login");
        }

        return ResponseEntity.ok(validCreds);
    }
}
