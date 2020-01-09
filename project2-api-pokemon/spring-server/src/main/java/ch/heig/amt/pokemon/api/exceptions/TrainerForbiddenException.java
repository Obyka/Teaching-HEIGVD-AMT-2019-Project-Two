package ch.heig.amt.pokemon.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TrainerForbiddenException extends RuntimeException {
    public TrainerForbiddenException(String exception) { super(exception); }
}
