package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
