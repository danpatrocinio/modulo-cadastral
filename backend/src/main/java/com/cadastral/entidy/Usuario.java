package com.cadastral.entidy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    @NotNull(message = "{Usuario.username.NotNull}")
    private String username;
    @NotNull(message = "{Usuario.senha.NotNull}")
    private String senha;
    private String nome;

}

