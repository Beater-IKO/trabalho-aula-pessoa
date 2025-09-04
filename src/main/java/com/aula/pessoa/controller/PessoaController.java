package com.aula.pessoa.controller;

import com.aula.pessoa.entities.Pessoa;
import com.aula.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public ResponseEntity<Pessoa> save (@Valid @RequestBody Pessoa pessoa) {
        var p =  service.save(pessoa);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() {
        var list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        var pessoa = service.findById(id);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        var p = service.update(id, pessoa);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
