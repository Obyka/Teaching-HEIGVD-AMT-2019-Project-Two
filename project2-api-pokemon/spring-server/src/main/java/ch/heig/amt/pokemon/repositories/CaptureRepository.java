package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.api.model.CapturePokemonTrainers;
import ch.heig.amt.pokemon.entities.CaptureEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CaptureRepository extends CrudRepository<CaptureEntity, Integer> {
    List<Optional<CaptureEntity>> findByidPokemon(Integer integer);
}
