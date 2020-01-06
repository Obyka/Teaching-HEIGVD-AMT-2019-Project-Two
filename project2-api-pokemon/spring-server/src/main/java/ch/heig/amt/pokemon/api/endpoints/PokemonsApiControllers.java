package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.PokemonsApi;
import ch.heig.amt.pokemon.api.exceptions.NotFoundException;
import ch.heig.amt.pokemon.api.exceptions.PokemonBadRequestException;
import ch.heig.amt.pokemon.api.exceptions.PokemonNotFoundException;
import ch.heig.amt.pokemon.api.model.Pokemon;
import ch.heig.amt.pokemon.entities.PokemonEntity;
import ch.heig.amt.pokemon.entities.UserEntity;
import ch.heig.amt.pokemon.repositories.PokemonRepository;
import ch.heig.amt.pokemon.repositories.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
public class PokemonsApiControllers implements PokemonsApi {

    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest request;

    /*
       URL : /pokemons
       method : POST with JSON
       Be careful to add Accept : application/json in header request
       may be implement in a TODO
     */
    public ResponseEntity<Pokemon> createPokemon(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Pokemon pokemon) {
        PokemonEntity pokemonEntity = toEntity(pokemon);

        pokemonEntity.setIdUser((Integer)request.getAttribute("idUser"));

        UserEntity userEntity = new UserEntity();
        userEntity.setId((Integer)request.getAttribute("idUser"));
        userEntity.setUsername((String)request.getAttribute("username"));

        userRepository.save(userEntity);
        PokemonEntity createdPokemonEntity = pokemonRepository.save(pokemonEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPokemonEntity.getPokeDexId()).toUri();

        return ResponseEntity.created(uri).body(toPokemon(createdPokemonEntity));
    }

    /*
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleCreationError(HttpMessageNotReadableException ex) {
        throw new PokemonBadRequestException("Pokemon not insered, bad JSON payload");
    }
    */
    /*
       URL : /pokemons/{id}
       method : DELETE
     */
    public ResponseEntity<Void> deletePokemonByID(@ApiParam(value = "The pokemon ID",required=true) @PathVariable("id") Integer id) {
        Optional<PokemonEntity> optionalPokemonEntity = pokemonRepository.findByPokeDexId(id);

        if(!optionalPokemonEntity.isPresent()) {
            throw new PokemonNotFoundException("Pokemon " + id + " not found");
        }

        pokemonRepository.deleteByPokeDexIdAndIdUser(id, (Integer)request.getAttribute("idUser"));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    /*
       URL : /pokemons/
       method : DELETE
     */
    public ResponseEntity<Void> deletePokemons() {
        pokemonRepository.deleteByIdUser((Integer)request.getAttribute("idUser"));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    /*
       URL : /pokemons/{id}
       method : GET
     */
    public ResponseEntity<Pokemon> getPokemonByID(@ApiParam(value = "The pokemon ID",required=true) @PathVariable("id") Integer id) {
        Optional<PokemonEntity> optionalPokemonEntity = pokemonRepository.findByPokeDexIdAndIdUser(id, (Integer)request.getAttribute("idUser"));

        if(!optionalPokemonEntity.isPresent()) {
            throw new PokemonNotFoundException("Pokemon " + id + " not found");
        }

        PokemonEntity pokemonEntity = optionalPokemonEntity.get();

        return ResponseEntity.ok(toPokemon(pokemonEntity));
    }

    /*
       URL : /pokemons
       method : GET
     */
    public ResponseEntity<List<Pokemon>> getPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        List<PokemonEntity> pokemonsEntities = new ArrayList<>();

        pokemonsEntities = (List<PokemonEntity>) pokemonRepository.findByIdUser((Integer)request.getAttribute("idUser"));

        for(PokemonEntity pokemonEntity : pokemonsEntities) {
            pokemons.add(toPokemon(pokemonEntity));
        }

        return ResponseEntity.ok(pokemons);
    }

    /*
       URL : /pokemons/{id}
       method : PUT
     */
    public ResponseEntity<Void> updatePokemonByID(@ApiParam(value = "The pokemon ID",required=true) @PathVariable("id") Integer id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Pokemon pokemon) {
        Optional<PokemonEntity> pokemonEntityOptional = pokemonRepository.findByPokeDexIdAndIdUser(id, (Integer)request.getAttribute("idUser"));

        if(!pokemonEntityOptional.isPresent()) {
            throw new PokemonNotFoundException("Pokemon " + id + " not found");
        }

        Pokemon pokemonToUpdate = toPokemon(pokemonEntityOptional.get());

        pokemonToUpdate.setPokedexId(pokemon.getPokedexId());
        pokemonToUpdate.setType(pokemon.getType());
        pokemonToUpdate.setHp(pokemon.getHp());
        pokemonToUpdate.setHeight(pokemon.getHeight());
        pokemonToUpdate.setCategory(pokemon.getCategory());

        pokemonRepository.save(toEntity(pokemonToUpdate));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* POJO to Entity conversion */
    private PokemonEntity toEntity(Pokemon pokemon) {
        PokemonEntity pokemonEntity = new PokemonEntity();

        pokemonEntity.setIdUser(pokemon.getIdUser());
        pokemonEntity.setPokeDexId(pokemon.getPokedexId());
        pokemonEntity.setName(pokemon.getName());
        pokemonEntity.setCategory(pokemon.getCategory());
        pokemonEntity.setHeight(pokemon.getHeight());
        pokemonEntity.setHp(pokemon.getHp());
        pokemonEntity.setType(pokemon.getType());

        return pokemonEntity;
    }

    /* Entity to POJO conversion */
    private Pokemon toPokemon(PokemonEntity pokemonEntity) {
        Pokemon pokemon = new Pokemon();

        pokemon.setIdUser(pokemonEntity.getIdUser());
        pokemon.setPokedexId(pokemonEntity.getPokeDexId());
        pokemon.setName(pokemonEntity.getName());
        pokemon.setCategory(pokemonEntity.getCategory());
        pokemon.setHeight(pokemonEntity.getHeight());
        pokemon.setHp(pokemonEntity.getHp());
        pokemon.setType(pokemonEntity.getType());

        return pokemon;
    }
}
