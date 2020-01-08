package ch.heig.amt.pokemon.repositories;

import ch.heig.amt.pokemon.entities.PokemonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface PokemonRepository  extends PagingAndSortingRepository<PokemonEntity, Integer> {
    Optional<PokemonEntity> findByPokeDexIdAndIdUser(Integer id, Integer idUser);

    Page<PokemonEntity> findAllByIdUser(Integer id , Pageable pageable);


    @Transactional
    Long deleteByIdUser(Integer id);

    @Transactional
    Long deleteByPokeDexIdAndIdUser(Integer id, Integer idUser);
}
