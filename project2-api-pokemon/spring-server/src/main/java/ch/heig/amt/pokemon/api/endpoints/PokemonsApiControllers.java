package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.PokemonsApi;
import ch.heig.amt.pokemon.api.exceptions.NotFoundException;
import ch.heig.amt.pokemon.api.exceptions.PokemonBadRequestException;
import ch.heig.amt.pokemon.api.exceptions.PokemonNotFoundException;
import ch.heig.amt.pokemon.api.model.Pokemon;
import ch.heig.amt.pokemon.api.model.PokemonPut;
import ch.heig.amt.pokemon.entities.PokemonEntity;
import ch.heig.amt.pokemon.entities.UserEntity;
import ch.heig.amt.pokemon.repositories.CaptureRepository;
import ch.heig.amt.pokemon.repositories.PokemonRepository;
import ch.heig.amt.pokemon.repositories.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private CaptureRepository captureRepository;
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
    public ResponseEntity<Pokemon> createPokemon(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PokemonPut pokemon) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        PokemonEntity pokemonEntity = toEntity(pokemon, idUser);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
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
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<PokemonEntity> optionalPokemonEntity = pokemonRepository.findByPokeDexIdAndIdUser(id, idUser);

        if(!optionalPokemonEntity.isPresent()) {
            throw new PokemonNotFoundException("Pokemon " + id + " not found");
        }

        pokemonRepository.deleteByPokeDexIdAndIdUser(id, idUser);

        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    /*
       URL : /pokemons/
       method : DELETE
     */
    public ResponseEntity<Void> deletePokemons() {
        Integer idUser = (Integer)request.getAttribute("idUser");

        pokemonRepository.deleteByIdUser(idUser);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    /*
       URL : /pokemons/{id}
       method : GET
     */
    public ResponseEntity<Pokemon> getPokemonByID(@ApiParam(value = "The pokemon ID",required=true) @PathVariable("id") Integer id) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<PokemonEntity> optionalPokemonEntity = pokemonRepository.findByPokeDexIdAndIdUser(id, idUser);

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
    public ResponseEntity<List<Pokemon>> getPokemons(@ApiParam(value = "The page number to get", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,@ApiParam(value = "The size of a page", defaultValue = "20") @Valid @RequestParam(value = "size", required = false, defaultValue="20") Integer size) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Pageable paging = PageRequest.of(page, size);
        Page<PokemonEntity> pagedPokemon = pokemonRepository.findAllByIdUser(idUser, paging);

        Page<Pokemon> pageResult = pagedPokemon.map(pokemonEntity -> toPokemon(pokemonEntity));
        return ResponseEntity.ok(pageResult.getContent());
    }

    /*
       URL : /pokemons/{id}
       method : PUT
     */
    public ResponseEntity<Void> updatePokemonByID(@ApiParam(value = "The pokemon ID",required=true) @PathVariable("id") Integer id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody PokemonPut pokemon) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<PokemonEntity> pokemonEntityOptional = pokemonRepository.findByPokeDexIdAndIdUser(id, idUser);

        if(!pokemonEntityOptional.isPresent()) {
            throw new PokemonNotFoundException("Pokemon " + id + " not found");
        }

        Pokemon pokemonToUpdate = toPokemon(pokemonEntityOptional.get());
        
        pokemonToUpdate.setPokedexId(pokemon.getPokedexId());
        pokemonToUpdate.setName(pokemon.getName());
        pokemonToUpdate.setType(pokemon.getType());
        pokemonToUpdate.setHp(pokemon.getHp());
        pokemonToUpdate.setHeight(pokemon.getHeight());
        pokemonToUpdate.setCategory(pokemon.getCategory());

        pokemonRepository.save(toEntity(pokemonToUpdate, idUser));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* POJO to Entity conversion */
    private PokemonEntity toEntity(PokemonPut pokemon, Integer idUser) {
        PokemonEntity pokemonEntity = new PokemonEntity();

        pokemonEntity.setIdUser(idUser);
        pokemonEntity.setPokeDexId(pokemon.getPokedexId());
        pokemonEntity.setName(pokemon.getName());
        pokemonEntity.setCategory(pokemon.getCategory());
        pokemonEntity.setHeight(pokemon.getHeight());
        pokemonEntity.setHp(pokemon.getHp());
        pokemonEntity.setType(pokemon.getType());

        return pokemonEntity;
    }

    /* POJO to Entity conversion */
    private PokemonEntity toEntity(Pokemon pokemon, Integer idUser) {
        PokemonEntity pokemonEntity = new PokemonEntity();

        pokemonEntity.setIdUser(idUser);
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
