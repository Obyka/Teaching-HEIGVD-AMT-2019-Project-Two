package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter

@Entity
public class UserEntity {
    @Id
    private Integer id;
    private String username;
}
