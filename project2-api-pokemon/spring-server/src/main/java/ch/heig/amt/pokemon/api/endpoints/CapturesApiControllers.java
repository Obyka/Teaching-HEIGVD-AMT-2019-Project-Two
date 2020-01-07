package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.CapturesApi;
import ch.heig.amt.pokemon.api.exceptions.CaptureNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    public ResponseEntity<CaptureAllWithId> createCapture(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Capture capture) {
        List<CapturePokemon> listCapturesPokemons = capture.getPokemons();

        UserEntity userEntity = new UserEntity();
        userEntity.setId((Integer)request.getAttribute("idUser"));
        userEntity.setUsername((String)request.getAttribute("username"));

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerIdAndIdUser(capture.getIdTrainer(), (Integer)request.getAttribute("idUser"));

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer with ID " + capture.getIdTrainer() + " does not belong to you.");
        }

        userRepository.save(userEntity);

        CaptureAllWithId captureAllWithId = new CaptureAllWithId();

        for(CapturePokemon pokemon : listCapturesPokemons) {
            CaptureEntity captureEntity = new CaptureEntity();

            captureEntity.setIdUser((Integer)request.getAttribute("idUser"));
            captureEntity.setIdTrainer(capture.getIdTrainer());
            captureEntity.setIdPokemon(pokemon.getIdPokemon());
            captureEntity.setDateCapture(pokemon.getDateCapture());

            Optional<PokemonEntity> optionalPokemonEntity = pokemonRepository.findByPokeDexIdAndIdUser(pokemon.getIdPokemon(), (Integer)request.getAttribute("idUser"));

            if(optionalPokemonEntity.isPresent()) {
                CaptureEntity addedCaptureEntity = captureRepository.save(captureEntity);

                CaptureWithId captureWithId = new CaptureWithId();
                CapturePokemon capturePokemon = new CapturePokemon();

                captureWithId.setId(addedCaptureEntity.getId());
                captureWithId.setIdTrainer(addedCaptureEntity.getIdTrainer());
                captureWithId.setIdUser(addedCaptureEntity.getIdUser());

                capturePokemon.setIdPokemon(addedCaptureEntity.getIdPokemon());
                capturePokemon.setDateCapture(addedCaptureEntity.getDateCapture());

                captureWithId.addPokemonsItem(capturePokemon);

                captureAllWithId.addAddedCapturesItem(captureWithId);
            }
        }

        return new ResponseEntity<>(captureAllWithId, HttpStatus.OK);
    }

    public ResponseEntity<CaptureGetAll> getPokemonWithTrainers(@ApiParam(value = "pokemon ID",required=true) @PathVariable("id_pokemon") Integer idPokemon) {
        List<CaptureEntity>  capturesEntities = captureRepository.findByIdPokemonAndIdUser(idPokemon, (Integer)request.getAttribute("idUser"));

        if(capturesEntities.isEmpty()) {
            throw new CaptureNotFoundException("Pokemon with ID " + idPokemon + " not found.");
        }

        return ResponseEntity.ok(getAndFetchResults(capturesEntities));
    }

    public ResponseEntity<CaptureGetAll> getTrainerWithPokemons(@ApiParam(value = "trainer ID",required=true) @PathVariable("id_trainer") Integer idTrainer) {
        List<CaptureEntity>  capturesEntities = captureRepository.findByIdTrainerAndIdUser(idTrainer, (Integer)request.getAttribute("idUser"));

        if(capturesEntities.isEmpty()) {
            throw new CaptureNotFoundException("Trainer with ID " + idTrainer + " not found.");
        }

        return ResponseEntity.ok(getAndFetchResults(capturesEntities));
    }

    private CaptureGetAll getAndFetchResults(List<CaptureEntity> capturesEntities) {
        CaptureGetAll captureGetAll = new CaptureGetAll();

        List<CaptureGet> captureGetList = new ArrayList<>();

        for(CaptureEntity captureEntity : capturesEntities) {
            CaptureGet captureGet = new CaptureGet();
            captureGet.setIdCapture(captureEntity.getId());
            captureGet.setIdUser(captureEntity.getIdUser());
            captureGet.setIdTrainer(captureEntity.getIdTrainer());
            captureGet.setIdPokemon(captureEntity.getIdPokemon());
            captureGet.setDateCapture(captureEntity.getDateCapture());

            //captureGetAll.addCapturesItem(captureGet);
            captureGetList.add(captureGet);
        }

        captureGetAll.setCaptures(captureGetList);
        return captureGetAll;
    }

}
