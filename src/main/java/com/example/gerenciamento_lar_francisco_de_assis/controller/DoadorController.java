package com.example.gerenciamento_lar_francisco_de_assis.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.DoadorDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Doador;
import com.example.gerenciamento_lar_francisco_de_assis.service.DoadorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/doadores")
@RequiredArgsConstructor
public class DoadorController {

    private final DoadorService doadorService;

    @PostMapping
    public ResponseEntity<Doador> criarDoador(@Valid @RequestBody DoadorDto dto) {
        Doador novoDoador = doadorService.criar(dto);
        return new ResponseEntity<>(novoDoador, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Doador>> listarDoadores() {
        return ResponseEntity.ok(doadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> buscarDoadorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(doadorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doador> atualizarDoador(
            @PathVariable Long id,
            @Valid @RequestBody DoadorDto dto) {
        return ResponseEntity.ok(doadorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDoador(@PathVariable Long id) {
        doadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}