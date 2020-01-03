package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.CapturesApi;
import ch.heig.amt.pokemon.api.exceptions.CaptureNotFoundException;
import ch.heig.amt.pokemon.api.model.*;
import ch.heig.amt.pokemon.entities.CaptureEntity;
import ch.heig.amt.pokemon.repositories.CaptureRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CapturesApiControllers implements CapturesApi {

    @Autowired
    CaptureRepository captureRepository;

    public ResponseEntity<CaptureWithId> createCapture(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Capture capture) {
        List<CapturePokemon> listCapturesPokemons = capture.getPokemons();

        for(CapturePokemon pokemon : listCapturesPokemons) {
            CaptureEntity captureEntity = new CaptureEntity();

            captureEntity.setIdTrainer(capture.getIdTrainer());
            captureEntity.setIdPokemon(pokemon.getIdPokemon());
            captureEntity.setDateCapture(pokemon.getDateCapture());

            captureRepository.save(captureEntity);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<CapturePokemonTrainers> getPokemonWithTrainers(@ApiParam(value = "pokemon ID",required=true) @PathVariable("id_pokemon") Integer idPokemon) {
        List<Optional<CaptureEntity>> optionalCapturePokemonTrainers = captureRepository.findByidPokemon(idPokemon);

        if(optionalCapturePokemonTrainers.isEmpty()) {
            throw new CaptureNotFoundException("Pokemon with ID : " + idPokemon + " not found.");
        }

        CapturePokemonTrainers capturePokemonTrainers = new CapturePokemonTrainers();
        CaptureTrainer captureTrainer = new CaptureTrainer();

        for(Optional<CaptureEntity> optionalCapturesEntity : optionalCapturePokemonTrainers) {
            CaptureEntity captureEntity = optionalCapturesEntity.get();

            captureTrainer.setDateCapture(captureEntity.getDateCapture());
            captureTrainer.setIdTrainer(captureEntity.getIdTrainer());

            capturePokemonTrainers.setId(captureEntity.getId());
            capturePokemonTrainers.setIdPokemon(captureEntity.getIdPokemon());
            capturePokemonTrainers.addTrainersItem(captureTrainer);
        }

        return ResponseEntity.ok(capturePokemonTrainers);
    }


    @ApiOperation(value = "", nickname = "getTrainerWithPokemons", notes = "Get a specific trainer with his all pokemons", response = CaptureTrainerPokemons.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", response = CaptureTrainerPokemons.class) })
    @RequestMapping(value = "/captures/trainers/{id_trainer}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<CaptureTrainerPokemons> getTrainerWithPokemons(@ApiParam(value = "trainer ID",required=true) @PathVariable("id_trainer") Integer idTrainer) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "null";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    private CaptureEntity toEntity(Capture capture) {
        CaptureEntity captureEntity = new CaptureEntity();

        captureEntity.setIdTrainer(capture.getIdTrainer());

        return captureEntity;
    }


}
