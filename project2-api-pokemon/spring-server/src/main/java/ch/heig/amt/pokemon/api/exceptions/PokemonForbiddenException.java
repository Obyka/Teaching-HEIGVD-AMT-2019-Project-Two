package ch.heig.amt.pokemon.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PokemonForbiddenException extends RuntimeException {
    public PokemonForbiddenException(String exception) { super(exception); }
}
