package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.CaptureEntity;
import org.springframework.data.repository.CrudRepository;

public interface CaptureRepository extends CrudRepository<CaptureEntity, Integer> {
}
