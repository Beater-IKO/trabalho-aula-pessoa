package com.aula.pessoa.service;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.aula.pessoa.config.exceptions.EntityNotFoundException;
import com.aula.pessoa.config.exceptions.FailedSaveException;
import com.aula.pessoa.entities.Pessoa;
import com.aula.pessoa.repository.PessoaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

  @InjectMocks
  PessoaService pessoaService;

  @Mock
  PessoaRepository pessoaRepository;

  Pessoa pessoa;

  @BeforeEach
  void setUp() {
    pessoa = new Pessoa(1L, "Roberto Santos",
        new Date(), "001.998.830-33", "roberto@email.com");
  }

  @Test
  @DisplayName("Validando o metodo de salvar pessoa")
  void cenario02() {
    var pessoaSave = new Pessoa();

    Mockito.when(pessoaRepository.save(pessoaSave)).thenReturn(pessoa);

    var resultado = pessoaService.save(pessoaSave);
    Assertions.assertEquals(pessoa, resultado);
  }

  @Test
  @DisplayName("Lancando excessao de integridade ao salvar o objeto")
  void cenario03() {

    Mockito.when(pessoaRepository.save(pessoa))
        .thenThrow(new DataIntegrityViolationException("duplicado"));

    FailedSaveException exception = Assertions.assertThrows(FailedSaveException.class, () -> {
      pessoaService.save(pessoa);
    });

    Assertions.assertEquals("Falha ao salvar, email ou cpf ja cadastrados", exception.getMessage());
  }

  @Test
  @DisplayName("Buscando pessoa por ID com sucesso")
  void testFindByIdSucesso() {
    Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    
    var resultado = pessoaService.findById(1L);
    Assertions.assertEquals(pessoa, resultado);
  }

  @Test
  @DisplayName("Lancando excecao quando pessoa nao encontrada")
  void testFindByIdErro() {
    Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());
    
    EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
      pessoaService.findById(1L);
    });
    
    Assertions.assertEquals("Usuario nao encontrado", exception.getMessage());
  }

  @Test
  @DisplayName("Listando todas as pessoas")
  void testFindAll() {
    List<Pessoa> pessoas = Arrays.asList(pessoa);
    Mockito.when(pessoaRepository.findAll()).thenReturn(pessoas);
    
    var resultado = pessoaService.findAll();
    Assertions.assertEquals(pessoas, resultado);
  }

  @Test
  @DisplayName("Atualizando pessoa com sucesso")
  void testUpdateSucesso() {
    var pessoaAtualizada = new Pessoa();
    pessoaAtualizada.setNome("Novo Nome");
    
    Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
    
    var resultado = pessoaService.update(1L, pessoaAtualizada);
    Assertions.assertEquals("Novo Nome", resultado.getNome());
  }

  @Test
  @DisplayName("Lancando excecao ao atualizar com dados duplicados")
  void testUpdateErro() {
    var pessoaAtualizada = new Pessoa();
    pessoaAtualizada.setEmail("email@duplicado.com");
    
    Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    Mockito.when(pessoaRepository.save(pessoa)).thenThrow(new DataIntegrityViolationException("duplicado"));
    
    FailedSaveException exception = Assertions.assertThrows(FailedSaveException.class, () -> {
      pessoaService.update(1L, pessoaAtualizada);
    });
    
    Assertions.assertEquals("Falha ao atualizar, email ou cpf ja cadastrados", exception.getMessage());
  }

  @Test
  @DisplayName("Deletando pessoa com sucesso")
  void testDeleteSucesso() {
    Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    
    Assertions.assertDoesNotThrow(() -> {
      pessoaService.delete(1L);
    });
    
    Mockito.verify(pessoaRepository).delete(pessoa);
  }

  @Test
  @DisplayName("Lancando excecao ao deletar pessoa inexistente")
  void testDeleteErro() {
    Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());
    
    EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
      pessoaService.delete(1L);
    });
    
    Assertions.assertEquals("Usuario nao encontrado", exception.getMessage());
  }

}