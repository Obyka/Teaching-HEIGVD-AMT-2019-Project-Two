package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.CaptureEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CaptureRepository extends CrudRepository<CaptureEntity, Integer> {
    List<CaptureEntity> findByIdPokemonAndIdUser(Integer id, Integer idUser);

    List<CaptureEntity> findByIdTrainerAndIdUser(Integer id, Integer idUser);

    @Transactional
    Long deleteByIdPokemonAndAndIdUser(Integer idPokemon, Integer idUser);

    @Transactional
    Long deleteByIdTrainerAndIdUser(Integer idTrainer, Integer idUser);

    @Transactional
    Long deleteByIdUser(Integer iduser);
}
