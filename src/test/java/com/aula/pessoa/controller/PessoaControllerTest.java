package com.aula.pessoa.controller;

import com.aula.pessoa.config.exceptions.EntityNotFoundException;
import com.aula.pessoa.config.exceptions.FailedSaveException;
import com.aula.pessoa.entities.Pessoa;
import com.aula.pessoa.service.PessoaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

  @Mock
  private PessoaService pessoaService;

  @InjectMocks
  private PessoaController pessoaController;

  private Pessoa pessoa;

  @BeforeEach
  void setUp() {
    pessoa = new Pessoa(1L, "João", new Date(), "123.456.789-00", "joao@email.com");
  }

  @Test
  void testSave() {
    Mockito.when(pessoaService.save(pessoa)).thenReturn(pessoa);

    var resultado = pessoaController.save(pessoa);

    Assertions.assertEquals(201, resultado.getStatusCode().value());
    Assertions.assertEquals("João", resultado.getBody().getNome());
  }

  @Test
  void testFindAll() {
    List<Pessoa> pessoas = Arrays.asList(pessoa);
    Mockito.when(pessoaService.findAll()).thenReturn(pessoas);

    var resultado = pessoaController.findAll();

    Assertions.assertEquals(200, resultado.getStatusCode().value());
    Assertions.assertEquals(1, resultado.getBody().size());
  }

  @Test
  void testFindById() {
    Mockito.when(pessoaService.findById(1L)).thenReturn(pessoa);

    var resultado = pessoaController.findById(1L);

    Assertions.assertEquals(200, resultado.getStatusCode().value());
    Assertions.assertEquals("João", resultado.getBody().getNome());
  }

  @Test
  void testUpdate() {
    Mockito.when(pessoaService.update(1L, pessoa)).thenReturn(pessoa);

    var resultado = pessoaController.update(1L, pessoa);

    Assertions.assertEquals(200, resultado.getStatusCode().value());
    Assertions.assertEquals("João", resultado.getBody().getNome());
  }

  @Test
  void testDelete() {
    Mockito.doNothing().when(pessoaService).delete(1L);

    var resultado = pessoaController.delete(1L);

    Assertions.assertEquals(204, resultado.getStatusCode().value());
  }

  @Test
  void testSaveErro() {
    Mockito.when(pessoaService.save(pessoa))
        .thenThrow(new FailedSaveException("Erro ao salvar"));

    Assertions.assertThrows(FailedSaveException.class, () -> {
      pessoaController.save(pessoa);
    });
  }

  @Test
  void testFindByIdErro() {
    Mockito.when(pessoaService.findById(1L))
        .thenThrow(new EntityNotFoundException("Pessoa não encontrada"));

    Assertions.assertThrows(EntityNotFoundException.class, () -> {
      pessoaController.findById(1L);
    });
  }

  @Test
  void testUpdateErro() {
    Mockito.when(pessoaService.update(1L, pessoa))
        .thenThrow(new FailedSaveException("Erro ao atualizar"));

    Assertions.assertThrows(FailedSaveException.class, () -> {
      pessoaController.update(1L, pessoa);
    });
  }

  @Test
  void testDeleteErro() {
    Mockito.doThrow(new EntityNotFoundException("Pessoa não encontrada"))
        .when(pessoaService).delete(1L);

    Assertions.assertThrows(EntityNotFoundException.class, () -> {
      pessoaController.delete(1L);
    });
  }
}