package com.example.gerenciamento_lar_francisco_de_assis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.EstoqueAlertaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Estoque;
import com.example.gerenciamento_lar_francisco_de_assis.service.EstoqueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/estoque")
@RequiredArgsConstructor
@Tag(name = "6. Estoque (Consultas)", description = "Endpoints para consultar o estado do estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    // GET /api/v1/estoque
    @Operation(summary = "Listar o status completo do estoque")
    @ApiResponse(responseCode = "200", description = "Lista retornada")
    @GetMapping
    public ResponseEntity<List<Estoque>> getEstoqueCompleto() {
        return ResponseEntity.ok(estoqueService.listarEstoqueCompleto());
    }

    // GET /api/v1/estoque/alertas
    @Operation(summary = "Listar itens com estoque abaixo do nível mínimo de alerta")
    @ApiResponse(responseCode = "200", description = "Lista retornada")
    @GetMapping("/alertas")
    public ResponseEntity<List<Estoque>> getEstoquesBaixos() {
        return ResponseEntity.ok(estoqueService.listarEstoquesBaixos());
    }

    // GET /api/v1/estoque/item?idProduto=1&idLocalizacao=2
    @Operation(summary = "Buscar um item de estoque por ID do Produto e ID da Localização")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item de estoque encontrado"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado nesta combinação")
    })
    @GetMapping("/item")
    public ResponseEntity<Estoque> getEstoquePorProdutoELocal(
            @RequestParam Long idProduto,
            @RequestParam Long idLocalizacao) {
        return ResponseEntity.ok(estoqueService.buscarEstoquePorProdutoELocal(idProduto, idLocalizacao));
    }

    // PATCH /api/v1/estoque/{id}/alerta
    @Operation(summary = "Definir o nível mínimo de alerta para um item de estoque")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nível de alerta atualizado"),
        @ApiResponse(responseCode = "404", description = "Item de estoque não encontrado")
    })
    @PatchMapping("/{idEstoque}/alerta")
    public ResponseEntity<Estoque> definirAlerta(
            @PathVariable Long idEstoque,
            @Valid @RequestBody EstoqueAlertaDto dto) {
        Estoque estoqueAtualizado = estoqueService.definirAlerta(idEstoque, dto);
        return ResponseEntity.ok(estoqueAtualizado);
    }
}