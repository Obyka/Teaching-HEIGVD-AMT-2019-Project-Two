package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter

@Entity
public class CaptureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trainer")
    private TrainerEntity trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pokemon")
    private PokemonEntity pokemon;

    private String dateCapture;
}
