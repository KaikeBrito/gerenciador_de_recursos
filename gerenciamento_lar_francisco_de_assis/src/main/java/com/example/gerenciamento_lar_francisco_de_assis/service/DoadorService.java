package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.DoadorDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Doador;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.BusinessRuleException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.mapper.DoadorMapper;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.DoadorRepository;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.EntradaDoacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoadorService {

    private final DoadorRepository doadorRepository;
    private final DoadorMapper doadorMapper;
    private final EntradaDoacaoRepository entradaDoacaoRepository;

    @Transactional(readOnly = true)
    public List<Doador> listarTodos() {
        return doadorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Doador buscarPorId(Long id) {
        return doadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doador não encontrado com ID: " + id));
    }

    @Transactional
    public Doador criar(DoadorDto dto) {
        if (dto.documento() != null && !dto.documento().isEmpty()) {
            doadorRepository.findByDocumento(dto.documento()).ifPresent(d -> {
                throw new BusinessRuleException("Documento '" + dto.documento() + "' já cadastrado.");
            });
        }

        Doador doador = doadorMapper.toEntity(dto, null);
        return doadorRepository.save(doador);
    }

    @Transactional
    public Doador atualizar(Long id, DoadorDto dto) {
        Doador doadorExistente = buscarPorId(id);

        if (dto.documento() != null && !dto.documento().isEmpty()) {
            doadorRepository.findByDocumento(dto.documento()).ifPresent(d -> {
                if (!d.getId().equals(id)) {
                    throw new BusinessRuleException("Documento '" + dto.documento() + "' já cadastrado.");
                }
            });
        }
        
        Doador doadorAtualizado = doadorMapper.toEntity(dto, doadorExistente);
        return doadorRepository.save(doadorAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Doador doador = buscarPorId(id);
        
        if (entradaDoacaoRepository.countByDoador(doador) > 0) {
            throw new BusinessRuleException("Não pode deletar doador com doações registradas.");
        }
        
        doadorRepository.delete(doador);
    }
}