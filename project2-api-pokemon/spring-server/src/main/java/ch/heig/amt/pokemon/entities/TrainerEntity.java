package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter

@Entity
public class TrainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer trainerId;

    @NotNull
    private Integer idUser;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String gender;

    private Integer age;

    private Integer numberOfBadges;
}
