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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/movimentacoes")
@RequiredArgsConstructor
@Tag(name = "5. Movimentações (Estoque)", description = "Endpoints para registrar entradas e saídas do estoque")
public class GestaoRecursosController {

    private final GestaoRecursosService gestaoRecursosService;

    // POST /api/v1/movimentacoes/entradas
    @Operation(summary = "Registrar uma nova entrada de doação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entrada registrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: Doador ou Produto não existe)"),
        @ApiResponse(responseCode = "403", description = "Acesso negado (Token inválido ou ausente)")
    })
    @PostMapping("/entradas")
    public ResponseEntity<EntradaDoacao> registrarEntrada(
            @Valid @RequestBody RegistrarEntradaDto dto) {
        EntradaDoacao novaEntrada = gestaoRecursosService.registrarEntrada(dto);
        return new ResponseEntity<>(novaEntrada, HttpStatus.CREATED);
    }

    // POST /api/v1/movimentacoes/saidas
    @Operation(summary = "Registrar uma nova saída de recursos (uso interno)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Saída registrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: Estoque insuficiente)"),
        @ApiResponse(responseCode = "403", description = "Acesso negado (Token inválido ou ausente)")
    })
    @PostMapping("/saidas")
    public ResponseEntity<SaidaRecurso> registrarSaida(
            @Valid @RequestBody RegistrarSaidaDto dto) {
        SaidaRecurso novaSaida = gestaoRecursosService.registrarSaida(dto);
        return new ResponseEntity<>(novaSaida, HttpStatus.CREATED);
    }
}