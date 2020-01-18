package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.CapturesApi;
import ch.heig.amt.pokemon.api.exceptions.CaptureNotFoundException;
import ch.heig.amt.pokemon.api.exceptions.PokemonNotFoundException;
import ch.heig.amt.pokemon.api.exceptions.TrainerNotFoundException;
import ch.heig.amt.pokemon.api.model.*;
import ch.heig.amt.pokemon.entities.CaptureEntity;
import ch.heig.amt.pokemon.entities.PokemonEntity;
import ch.heig.amt.pokemon.entities.TrainerEntity;
import ch.heig.amt.pokemon.entities.UserEntity;
import ch.heig.amt.pokemon.repositories.CaptureRepository;
import ch.heig.amt.pokemon.repositories.PokemonRepository;
import ch.heig.amt.pokemon.repositories.TrainerRepository;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CapturesApiControllers implements CapturesApi {

    @Autowired
    CaptureRepository captureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    PokemonRepository pokemonRepository;

    public ResponseEntity<CaptureGet> createCapture(@ApiParam(value = "" ,required=true )  @Valid @RequestBody CapturePost capture) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
        userEntity.setUsername((String)request.getAttribute("username"));


        userRepository.save(userEntity);
        CaptureEntity captureReturned = captureRepository.save(toEntity(capture));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(captureReturned.getId()).toUri();

        return ResponseEntity.created(uri).body(toCaptureGet(captureReturned));
    }

    public ResponseEntity<List<CaptureGet>> getPokemonWithTrainers(@ApiParam(value = "pokemon ID",required=true) @PathVariable("id_pokemon") Integer idPokemon,@ApiParam(value = "The page number to get", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,@ApiParam(value = "The size of a page", defaultValue = "20") @Valid @RequestParam(value = "size", required = false, defaultValue="20") Integer size) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<PokemonEntity> optionalPokemon = pokemonRepository.findByPokeDexIdAndIdUser(idPokemon, idUser);

        if(!optionalPokemon.isPresent()) {
            throw new PokemonNotFoundException("Pokemon with id " + idPokemon + " not found or does not belong to you");
        }

        PokemonEntity pokemonEntity = optionalPokemon.get();
        Pageable paging = PageRequest.of(page, size);
        Page<CaptureEntity> captureEntityPage = captureRepository.findAllByPokemonAndIdUser(pokemonEntity,idUser, paging);

        Page<CaptureGet> pageResult = captureEntityPage.map(this::toCaptureGet);
        return ResponseEntity.ok(pageResult.getContent());
    }

    public ResponseEntity<List<CaptureGet>> getTrainerWithPokemons(@ApiParam(value = "trainer ID",required=true) @PathVariable("id_trainer") Integer idTrainer,@ApiParam(value = "The page number to get", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,@ApiParam(value = "The size of a page", defaultValue = "20") @Valid @RequestParam(value = "size", required = false, defaultValue="20") Integer size) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerIdAndIdUser(idTrainer, idUser);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer with ID " + idTrainer + " not found or does not belong to you");
        }

        TrainerEntity trainerEntity = optionalTrainerEntity.get();
        Pageable paging = PageRequest.of(page, size);
        Page<CaptureEntity> captureEntityPage = captureRepository.findAllByTrainerAndIdUser(trainerEntity,idUser, paging);

        Page<CaptureGet> pageResult = captureEntityPage.map(this::toCaptureGet);
        return ResponseEntity.ok(pageResult.getContent());
    }

    private CaptureGet toCaptureGet(CaptureEntity captureEntity) {
        CaptureGet captureGet = new CaptureGet();

        captureGet.setDateCapture(captureEntity.getDateCapture());
        captureGet.setIdCapture(captureEntity.getId());
        captureGet.setIdPokemon(captureEntity.getPokemon().getPokeDexId());
        captureGet.setIdTrainer(captureEntity.getTrainer().getTrainerId());
        captureGet.setIdUser(captureEntity.getIdUser());
        return captureGet;
    }

    private CaptureEntity toEntity(CapturePost capture) {
        Integer idUser = (Integer)request.getAttribute("idUser");
        CaptureEntity captureEntity = new CaptureEntity();


        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerIdAndIdUser(capture.getIdTrainer(), idUser);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer with ID " + capture.getIdTrainer() + " does not belong to you or does not exist.");
        }

        Optional<PokemonEntity> optionalPokemonEntity = pokemonRepository.findByPokeDexIdAndIdUser(capture.getIdPokemon(), idUser);

        if(!optionalPokemonEntity.isPresent()) {
            throw new PokemonNotFoundException("Pokemon with ID " + capture.getIdPokemon() + " does not belong to you or does not exist.");
        }

        captureEntity.setIdUser(idUser);
        captureEntity.setTrainer(optionalTrainerEntity.get());
        captureEntity.setPokemon(optionalPokemonEntity.get());
        captureEntity.setDateCapture(capture.getDateCapture());

        return captureEntity;
    }
}
