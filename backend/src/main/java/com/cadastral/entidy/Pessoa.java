package com.cadastral.entidy;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Audited
public class Pessoa extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @CPF(message = "{Generic.invalid.cpf}")
    @Column(unique = true)
    @NotNull(message = "{Pessoa.cpf.NotNull}")
    private String cpf;
    @NotNull(message = "{Pessoa.nome.NotNull}")
    private String nome;
    @Email(message = "{Generic.invalid.email}")
    private String email;
    @Past(message = "{Pessoa.dtNascimento.Past}")
    @NotNull(message = "{Pessoa.dtNascimento.NotNull}")
    private LocalDate dataNascimento;
    private String sexo;
    private String naturalidade;
    private String nacionalidade;

}
