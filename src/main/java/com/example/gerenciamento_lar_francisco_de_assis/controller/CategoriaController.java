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

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.CategoriaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Categoria;
import com.example.gerenciamento_lar_francisco_de_assis.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categorias") // Define o caminho base para este recurso
@RequiredArgsConstructor
@Tag(name = "2. Categorias", description = "CRUD de Categorias de Produtos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    // POST /api/v1/categorias
    @Operation(summary = "Criar nova categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria criada"),
        @ApiResponse(responseCode = "400", description = "Nome da categoria já existe"),
        @ApiResponse(responseCode = "403", description = "Acesso negado (Token inválido ou ausente)")
    })
    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@Valid @RequestBody CategoriaDto dto) {
        Categoria novaCategoria = categoriaService.criar(dto);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }

    // GET /api/v1/categorias
    @Operation(summary = "Listar todas as categorias")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    // GET /api/v1/categorias/{id}
    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // PUT /api/v1/categorias/{id}
    @Operation(summary = "Atualizar categoria por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDto dto) {
        Categoria categoriaAtualizada = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    // DELETE /api/v1/categorias/{id}
    @Operation(summary = "Deletar categoria por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria deletada"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}