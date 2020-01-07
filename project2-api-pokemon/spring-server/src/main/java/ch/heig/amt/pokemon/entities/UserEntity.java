package ch.heig.amt.pokemon.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter

@Entity
public class UserEntity {
    @Id
    private Integer id;
    @NotNull
    @Column(unique=true)
    private String username;
}
