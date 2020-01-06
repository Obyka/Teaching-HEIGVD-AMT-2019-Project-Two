package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.TrainerEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends CrudRepository<TrainerEntity, Integer> {
    @Transactional
    Long deleteByTrainerIdAndIdUser(Integer id, Integer idUser);

    @Transactional
    Long deleteByIdUser(Integer idUser);

    Optional<TrainerEntity> findByTrainerIdAndIdUser(Integer id, Integer idUser);
    List<TrainerEntity> findByIdUser(Integer idUser);
}
