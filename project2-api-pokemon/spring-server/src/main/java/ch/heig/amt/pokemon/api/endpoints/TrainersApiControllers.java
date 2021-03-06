package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.TrainersApi;
import ch.heig.amt.pokemon.api.exceptions.TrainerNotFoundException;
import ch.heig.amt.pokemon.api.model.Pokemon;
import ch.heig.amt.pokemon.api.model.Trainer;
import ch.heig.amt.pokemon.api.model.TrainerPut;
import ch.heig.amt.pokemon.api.model.TrainerWithId;
import ch.heig.amt.pokemon.entities.PokemonEntity;
import ch.heig.amt.pokemon.entities.TrainerEntity;
import ch.heig.amt.pokemon.entities.UserEntity;
import ch.heig.amt.pokemon.repositories.CaptureRepository;
import ch.heig.amt.pokemon.repositories.TrainerRepository;
import ch.heig.amt.pokemon.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
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
public class TrainersApiControllers implements TrainersApi {

    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest request;

    public ResponseEntity<TrainerWithId> createTrainer(@ApiParam(value = "" ,required=true )  @Valid @RequestBody TrainerPut trainer) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        TrainerEntity trainerEntity = toTrainerEntity(trainer, idUser);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
        userEntity.setUsername((String)request.getAttribute("username"));

        userRepository.save(userEntity);

        TrainerEntity createdTrainerEntity = trainerRepository.save(trainerEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTrainerEntity.getTrainerId()).toUri();

        TrainerWithId createdTrainerWithId = toTrainerWithId(createdTrainerEntity);

        return ResponseEntity.created(uri).body(createdTrainerWithId);
    }


    public ResponseEntity<Void> deleteTrainerById(@ApiParam(value = "The trainer ID",required=true) @PathVariable("id") Integer id) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerIdAndIdUser(id, idUser);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer not found");
        }

        trainerRepository.deleteByTrainerIdAndIdUser(id, idUser);

        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    public ResponseEntity<Void> deleteTrainers() {
        Integer idUser = (Integer)request.getAttribute("idUser");

        trainerRepository.deleteByIdUser(idUser);

        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    public ResponseEntity<TrainerWithId> getTrainerById(@ApiParam(value = "The trainer ID",required=true) @PathVariable("id") Integer id) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerIdAndIdUser(id, idUser);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer not found");
        }

        TrainerEntity trainerEntity = optionalTrainerEntity.get();

        return ResponseEntity.ok(toTrainerWithId(trainerEntity));
    }


    public ResponseEntity<List<TrainerWithId>> getTrainers(@ApiParam(value = "The page number to get", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,@ApiParam(value = "The size of a page", defaultValue = "20") @Valid @RequestParam(value = "size", required = false, defaultValue="20") Integer size) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Pageable paging = PageRequest.of(page, size);
        Page<TrainerEntity> pagedTrainer = trainerRepository.findAllByIdUser(idUser, paging);

        Page<TrainerWithId> pageResult = pagedTrainer.map(trainerEntity -> toTrainerWithId(trainerEntity));
        return ResponseEntity.ok(pageResult.getContent());
    }

    public ResponseEntity<Void> updateTrainerById(@ApiParam(value = "The trainer ID",required=true) @PathVariable("id") Integer id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody TrainerPut trainer) {
        Integer idUser = (Integer)request.getAttribute("idUser");

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerIdAndIdUser(id, idUser);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer not found");
        }

        TrainerWithId updatedTrainer = addID(id, trainer, idUser);
        trainerRepository.save(toTrainerEntity(updatedTrainer));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private TrainerWithId addID(int id, TrainerPut trainer, int idUser){
        TrainerWithId trainerWithId = new TrainerWithId();

        trainerWithId.setId(id);
        trainerWithId.setIdUser(idUser);
        trainerWithId.setSurname(trainer.getSurname());
        trainerWithId.setName(trainer.getName());
        trainerWithId.setNumberOfBadges(trainer.getNumberOfBadges());
        trainerWithId.setGender(trainer.getGender());
        trainerWithId.setAge(trainer.getAge());
        return trainerWithId;
    }

    /* Entity to POJO conversion */
    private TrainerWithId toTrainerWithId(TrainerEntity trainerEntity) {
        TrainerWithId trainerWithId = new TrainerWithId();

        trainerWithId.setId(trainerEntity.getTrainerId());
        trainerWithId.setIdUser(trainerEntity.getIdUser());
        trainerWithId.setAge(trainerEntity.getAge());
        trainerWithId.setGender(trainerEntity.getGender());
        trainerWithId.setName(trainerEntity.getName());
        trainerWithId.setSurname(trainerEntity.getSurname());
        trainerWithId.setNumberOfBadges(trainerEntity.getNumberOfBadges());

        return trainerWithId;
    }

    private TrainerEntity toTrainerEntity(TrainerPut trainer, Integer idUser) {
        TrainerEntity trainerEntity = new TrainerEntity();

        trainerEntity.setIdUser(idUser);
        trainerEntity.setAge(trainer.getAge());
        trainerEntity.setGender(trainer.getGender());
        trainerEntity.setName(trainer.getName());
        trainerEntity.setNumberOfBadges(trainer.getNumberOfBadges());
        trainerEntity.setSurname(trainer.getSurname());

        return trainerEntity;
    }

    private TrainerEntity toTrainerEntity(TrainerWithId trainerWithId) {
        TrainerEntity trainerEntity = new TrainerEntity();

        trainerEntity.setTrainerId(trainerWithId.getId());
        trainerEntity.setIdUser(trainerWithId.getIdUser());
        trainerEntity.setAge(trainerWithId.getAge());
        trainerEntity.setGender(trainerWithId.getGender());
        trainerEntity.setName(trainerWithId.getName());
        trainerEntity.setNumberOfBadges(trainerWithId.getNumberOfBadges());
        trainerEntity.setSurname(trainerWithId.getSurname());

        return trainerEntity;
    }

}