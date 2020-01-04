package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.joda.time.DateTime;

@Getter
@Setter

@Entity
public class CaptureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer idTrainer;

    private Integer idPokemon;

    private String dateCapture;
}
