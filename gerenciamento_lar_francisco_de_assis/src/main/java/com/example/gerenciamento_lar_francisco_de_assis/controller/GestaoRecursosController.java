package com.example.gerenciamento_lar_francisco_de_assis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.RegistrarEntradaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.RegistrarSaidaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.EntradaDoacao;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.SaidaRecurso;
import com.example.gerenciamento_lar_francisco_de_assis.service.GestaoRecursosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/movimentacoes")
@RequiredArgsConstructor
public class GestaoRecursosController {

    private final GestaoRecursosService gestaoRecursosService;

    // POST /api/v1/movimentacoes/entradas
    @PostMapping("/entradas")
    public ResponseEntity<EntradaDoacao> registrarEntrada(
            @Valid @RequestBody RegistrarEntradaDto dto) {
        EntradaDoacao novaEntrada = gestaoRecursosService.registrarEntrada(dto);
        return new ResponseEntity<>(novaEntrada, HttpStatus.CREATED);
    }

    // POST /api/v1/movimentacoes/saidas
    @PostMapping("/saidas")
    public ResponseEntity<SaidaRecurso> registrarSaida(
            @Valid @RequestBody RegistrarSaidaDto dto) {
        SaidaRecurso novaSaida = gestaoRecursosService.registrarSaida(dto);
        return new ResponseEntity<>(novaSaida, HttpStatus.CREATED);
    }
}