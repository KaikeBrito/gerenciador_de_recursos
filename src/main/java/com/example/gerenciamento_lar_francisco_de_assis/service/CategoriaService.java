package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.CategoriaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Categoria;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.BusinessRuleException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.mapper.CategoriaMapper;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }

    @Transactional
    public Categoria criar(CategoriaDto dto) {
        categoriaRepository.findByNomeIgnoreCase(dto.nome()).ifPresent(c -> {
            throw new BusinessRuleException("Categoria com nome '" + dto.nome() + "' já existe.");
        });

        Categoria categoria = categoriaMapper.toEntity(dto, null);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria atualizar(Long id, CategoriaDto dto) {
        Categoria categoriaExistente = buscarPorId(id);
        
        categoriaRepository.findByNomeIgnoreCase(dto.nome()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new BusinessRuleException("Nome '" + dto.nome() + "' já está em uso por outra categoria.");
            }
        });

        Categoria categoriaAtualizada = categoriaMapper.toEntity(dto, categoriaExistente);
        return categoriaRepository.save(categoriaAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }
}