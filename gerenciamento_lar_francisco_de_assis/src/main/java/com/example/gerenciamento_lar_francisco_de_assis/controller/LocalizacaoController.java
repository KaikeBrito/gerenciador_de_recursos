package com.example.gerenciamento_lar_francisco_de_assis.controller;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.LocalizacaoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import com.example.gerenciamento_lar_francisco_de_assis.service.LocalizacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/localizacoes")
@RequiredArgsConstructor
@Tag(name = "8. Localizações", description = "CRUD para gerenciamento de Localizações de estoque")
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;

    @Operation(summary = "Criar nova localização de estoque")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Localização criada"),
        @ApiResponse(responseCode = "400", description = "Nome da localização já existe")
    })
    @PostMapping
    public ResponseEntity<Localizacao> criarLocalizacao(@Valid @RequestBody LocalizacaoDto dto) {
        Localizacao novaLocalizacao = localizacaoService.criar(dto);
        return new ResponseEntity<>(novaLocalizacao, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todas as localizações")
    @ApiResponse(responseCode = "200", description = "Lista retornada")
    @GetMapping
    public ResponseEntity<List<Localizacao>> listarLocalizacoes() {
        return ResponseEntity.ok(localizacaoService.listarTodos());
    }

    @Operation(summary = "Buscar localização por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Localização encontrada"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Localizacao> buscarLocalizacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localizacaoService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar localização por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Localização atualizada"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Localizacao> atualizarLocalizacao(
            @PathVariable Long id,
            @Valid @RequestBody LocalizacaoDto dto) {
        return ResponseEntity.ok(localizacaoService.atualizar(id, dto));
    }

    @Operation(summary = "Deletar localização por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Localização deletada"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocalizacao(@PathVariable Long id) {
        localizacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}