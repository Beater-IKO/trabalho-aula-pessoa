package com.aula.pessoa.entities;


import jakarta.persistence.*;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "O nome nao pode estar em branco ou vazio")
    @Size(max = 150)
    private String nome;

    @Column(nullable = false)
    @PastOrPresent(message = "A data de nascimento deve ser menor ou igual a data atual")
    private Date dataNascimento;

    @Column(nullable = false, unique = true)
    @CPF(message = " O cpf deve estar em um formato valido ")
    private String cpf;

    @Column(nullable = false, unique = true)
    @Email(message = "O email deve estar em um formato valido")
    private String email;


}
