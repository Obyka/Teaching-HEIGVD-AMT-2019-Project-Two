package ch.heig.amt.pokemon.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter

@Entity
public class PokemonEntity {
    @Id
    private Integer pokeDexId;

    @NotNull
    private Integer idUser;

    @NotNull
    private String name;

    private String type;

    private String category;

    private Integer height;

    private Integer hp;
}
