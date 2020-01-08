package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.api.model.Trainer;
import ch.heig.amt.pokemon.entities.CaptureEntity;
import ch.heig.amt.pokemon.entities.PokemonEntity;
import ch.heig.amt.pokemon.entities.TrainerEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CaptureRepository extends CrudRepository<CaptureEntity, Integer> {
    List<CaptureEntity> findByPokemonAndIdUser(PokemonEntity pokemonEntity, Integer idUser);

    List<CaptureEntity> findByTrainerAndIdUser(TrainerEntity trainerEntity, Integer idUser);

    /*
    @Transactional
    Long deleteByPokemonAndIdUser(PokemonEntity pokemon, Integer idUser);

    @Transactional
    Long deleteByTrainerAndIdUser(TrainerEntity trainer, Integer idUser);

    @Transactional
    Long deleteByIdUser(Integer iduser);
    */

}
