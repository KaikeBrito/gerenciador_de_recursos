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

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.ProdutoDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Produto;
import com.example.gerenciamento_lar_francisco_de_assis.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
@Tag(name = "3. Produtos", description = "CRUD para gerenciamento de Produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    
    @Operation(summary = "Criar um novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: nome duplicado, categoria não existe)"),
        @ApiResponse(responseCode = "403", description = "Acesso negado (Token inválido ou ausente)")
    })
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody ProdutoDto dto) {
        Produto novoProduto = produtoService.criar(dto); // O Service deve buscar a Categoria pelo ID
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada")
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @Operation(summary = "Buscar um produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar um produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDto dto) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
    }

    @Operation(summary = "Deletar um produto pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "400", description = "Não é possível deletar (ex: produto está em uso no estoque)"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Exemplo de endpoint extra: Listar produtos por categoria
    @Operation(summary = "Listar produtos por ID da categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos retornada"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/por-categoria/{idCategoria}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(produtoService.buscarPorCategoria(idCategoria));
    }
}