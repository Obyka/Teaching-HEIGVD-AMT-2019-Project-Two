package ch.heig.amt.login.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Column(unique=true)
    private String username;
    private String mail;
    private String firstname;
    private String lastname;
    @NotNull
    private String password;
    @NotNull
    private Boolean isadmin;
}
