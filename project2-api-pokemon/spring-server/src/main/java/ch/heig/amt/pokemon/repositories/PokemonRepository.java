package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends CrudRepository<PokemonEntity, Integer> {
    List<PokemonEntity> findByIdUser(Integer id);
    Optional<PokemonEntity> findByPokeDexIdAndIdUser(Integer id, Integer idUser);

    @Transactional
    Long deleteByIdUser(Integer id);

    @Transactional
    Long deleteByPokeDexIdAndIdUser(Integer id, Integer idUser);
}
