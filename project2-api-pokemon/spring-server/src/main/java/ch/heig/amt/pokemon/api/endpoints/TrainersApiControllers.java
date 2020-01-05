package ch.heig.amt.pokemon.api.endpoints;

import ch.heig.amt.pokemon.api.ApiUtil;
import ch.heig.amt.pokemon.api.TrainersApi;
import ch.heig.amt.pokemon.api.exceptions.TrainerNotFoundException;
import ch.heig.amt.pokemon.api.model.Trainer;
import ch.heig.amt.pokemon.api.model.TrainerWithId;
import ch.heig.amt.pokemon.entities.TrainerEntity;
import ch.heig.amt.pokemon.repositories.TrainerRepository;
import ch.heig.amt.pokemon.repositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TrainerRepository trainerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest request;

    public ResponseEntity<TrainerWithId> createTrainer(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Trainer trainer) {
        TrainerEntity trainerEntity = toTrainerEntity(trainer);

        trainerEntity.setIdUser((Integer)request.getAttribute("idUser"));

        TrainerEntity createdTrainerEntity = trainerRepository.save(trainerEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTrainerEntity.getTrainerId()).toUri();

        TrainerWithId createdTrainerWithId = toTrainerWithId(createdTrainerEntity);

        return ResponseEntity.created(uri).body(createdTrainerWithId);
    }


    public ResponseEntity<Void> deleteTrainerById(@ApiParam(value = "The trainer ID",required=true) @PathVariable("id") Integer id) {
        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findById(id);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer not found");
        }

        trainerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }


    public ResponseEntity<Void> deleteTrainers() {
        trainerRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    public ResponseEntity<TrainerWithId> getTrainerById(@ApiParam(value = "The trainer ID",required=true) @PathVariable("id") Integer id) {
        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findById(id);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer not found");
        }

        TrainerEntity trainerEntity = optionalTrainerEntity.get();

        return ResponseEntity.ok(toTrainerWithId(trainerEntity));
    }


    public ResponseEntity<List<TrainerWithId>> getTrainers() {
        List<TrainerWithId> trainers = new ArrayList<>();
        List<TrainerEntity> trainerEntities = new ArrayList<>();

        trainerEntities = (List<TrainerEntity>) trainerRepository.findAll();

        for(TrainerEntity trainerEntity : trainerEntities) {
            trainers.add(toTrainerWithId(trainerEntity));
        }

        return ResponseEntity.ok(trainers);
    }

    public ResponseEntity<Void> updateTrainerById(@ApiParam(value = "The trainer ID",required=true) @PathVariable("id") Integer id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody Trainer trainer) {
        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findById(id);

        if(!optionalTrainerEntity.isPresent()) {
            throw new TrainerNotFoundException("Trainer not found");
        }

        TrainerWithId updatedTrainer = addID(id,trainer);
        trainerRepository.save(toTrainerEntity(updatedTrainer));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private TrainerWithId addID(int id, Trainer trainer){
        TrainerWithId trainerWithId = new TrainerWithId();
        trainerWithId.setId(id);
        trainerWithId.setIdUser(trainer.getIdUser());
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
        trainerWithId.setNumberOfBadges(trainerEntity.getNumberOfBadges());
        trainerWithId.setSurname(trainerEntity.getSurname());

        return trainerWithId;
    }

    private TrainerEntity toTrainerEntity(Trainer trainer) {
        TrainerEntity trainerEntity = new TrainerEntity();

        trainerEntity.setIdUser(trainer.getIdUser());
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