package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.api.model.Trainer;
import ch.heig.amt.pokemon.entities.CaptureEntity;
import ch.heig.amt.pokemon.entities.PokemonEntity;
import ch.heig.amt.pokemon.entities.TrainerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CaptureRepository extends PagingAndSortingRepository<CaptureEntity, Integer> {
    Page<CaptureEntity> findAllByPokemonAndIdUser(PokemonEntity pokemonEntity, Integer idUser, Pageable page);

    Page<CaptureEntity> findAllByTrainerAndIdUser(TrainerEntity trainerEntity, Integer idUser, Pageable page);


    /*
    @Transactional
    Long deleteByPokemonAndIdUser(PokemonEntity pokemon, Integer idUser);

    @Transactional
    Long deleteByTrainerAndIdUser(TrainerEntity trainer, Integer idUser);

    @Transactional
    Long deleteByIdUser(Integer iduser);
    */

}
