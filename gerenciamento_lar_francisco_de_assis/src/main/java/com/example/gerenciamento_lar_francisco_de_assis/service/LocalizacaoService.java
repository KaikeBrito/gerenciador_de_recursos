package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.LocalizacaoDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.BusinessRuleException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.mapper.LocalizacaoMapper;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.EstoqueRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.LocalizacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalizacaoService {

    private final LocalizacaoRepository localizacaoRepository;
    private final LocalizacaoMapper localizacaoMapper;
    private final EstoqueRepository estoqueRepository; 

    @Transactional(readOnly = true)
    public List<Localizacao> listarTodos() {
        return localizacaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Localizacao buscarPorId(Long id) {
        return localizacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localização não encontrada com ID: " + id));
    }

    @Transactional
    public Localizacao criar(LocalizacaoDto dto) {
        localizacaoRepository.findByNomeIgnoreCase(dto.nome()).ifPresent(l -> {
            throw new BusinessRuleException("Localização com nome '" + dto.nome() + "' já existe.");
        });

        Localizacao localizacao = localizacaoMapper.toEntity(dto, null);
        return localizacaoRepository.save(localizacao);
    }

    @Transactional
    public Localizacao atualizar(Long id, LocalizacaoDto dto) {
        Localizacao localizacaoExistente = buscarPorId(id);

        localizacaoRepository.findByNomeIgnoreCase(dto.nome()).ifPresent(l -> {
            if (!l.getId().equals(id)) {
                throw new BusinessRuleException("Nome '" + dto.nome() + "' já está em uso por outra localização.");
            }
        });

        Localizacao localizacaoAtualizada = localizacaoMapper.toEntity(dto, localizacaoExistente);
        return localizacaoRepository.save(localizacaoAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        Localizacao localizacao = buscarPorId(id);
        

        if (estoqueRepository.countByLocalizacao(localizacao) > 0) {
            throw new BusinessRuleException("Não pode deletar localização com estoque associado.");
        }
        
        localizacaoRepository.delete(localizacao);
    }
}