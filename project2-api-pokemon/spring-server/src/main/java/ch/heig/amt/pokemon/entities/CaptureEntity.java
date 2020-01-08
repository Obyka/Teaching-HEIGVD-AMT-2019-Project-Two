package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

@Getter
@Setter

@Entity
public class CaptureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer idUser;

    @NotNull
    private Integer idTrainer;

    @NotNull
    private Integer idPokemon;

    private String dateCapture;
}
