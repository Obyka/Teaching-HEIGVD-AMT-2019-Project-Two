package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.joda.time.DateTime;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrainerEntity trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pokemon")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PokemonEntity pokemon;

    private String dateCapture;
}
