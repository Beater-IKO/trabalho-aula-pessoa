package com.aula.pessoa.repository;

import com.aula.pessoa.entities.Pessoa;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
