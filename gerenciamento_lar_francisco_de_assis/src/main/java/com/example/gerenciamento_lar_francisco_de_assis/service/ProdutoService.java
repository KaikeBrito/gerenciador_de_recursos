package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.ProdutoDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Categoria;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Produto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.BusinessRuleException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.mapper.ProdutoMapper;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.CategoriaRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.EstoqueRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.ProdutoRepository;

// import com.example.gerenciamento_lar_francisco_de_assis.domain.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoMapper produtoMapper;
    private final EstoqueRepository estoqueRepository;

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(Long idCategoria) {
        if (!categoriaRepository.existsById(idCategoria)) {
             throw new ResourceNotFoundException("Categoria não encontrada com ID: " + idCategoria);
        }
        return produtoRepository.findByCategoriaId(idCategoria);
    }

    @Transactional
    public Produto criar(ProdutoDto dto) {
        produtoRepository.findByNomeIgnoreCase(dto.nome()).ifPresent(p -> {
            throw new BusinessRuleException("Produto com nome '" + dto.nome() + "' já existe.");
        });

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + dto.idCategoria()));

        Produto produto = produtoMapper.toEntity(dto, null);
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoDto dto) {
        Produto produtoExistente = buscarPorId(id);

        produtoRepository.findByNomeIgnoreCase(dto.nome()).ifPresent(p -> {
            if (!p.getId().equals(id)) {
                throw new BusinessRuleException("Nome '" + dto.nome() + "' já está em uso por outro produto.");
            }
        });

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + dto.idCategoria()));

        Produto produtoAtualizado = produtoMapper.toEntity(dto, produtoExistente);
        produtoAtualizado.setCategoria(categoria);

        return produtoRepository.save(produtoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        
        // REGRA DE NEGÓCIO IMPORTANTE:
        if (estoqueRepository.countByProduto(produto) > 0) {
            throw new BusinessRuleException("Não pode deletar produto com estoque associado.");
        }

        produtoRepository.delete(produto);
    }
}