package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.TrainerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends PagingAndSortingRepository<TrainerEntity, Integer> {
    @Transactional
    Long deleteByTrainerIdAndIdUser(Integer id, Integer idUser);

    @Transactional
    Long deleteByIdUser(Integer idUser);

    Optional<TrainerEntity> findByTrainerIdAndIdUser(Integer id, Integer idUser);
    Page<TrainerEntity> findAllByIdUser(Integer idUser, Pageable page);
}
