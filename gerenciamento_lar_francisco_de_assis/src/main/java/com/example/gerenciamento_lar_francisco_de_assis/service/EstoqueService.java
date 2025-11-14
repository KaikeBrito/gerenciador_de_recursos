package com.example.gerenciamento_lar_francisco_de_assis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.EstoqueAlertaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Estoque;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Produto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.BusinessRuleException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.exception.ResourceNotFoundException;
import com.example.gerenciamento_lar_francisco_de_assis.domain.respository.EstoqueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    // Métodos de consulta (Usados pelo Dashboard/Relatórios)

    @Transactional(readOnly = true)
    public List<Estoque> listarEstoqueCompleto() {
        return estoqueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Estoque> listarEstoquesBaixos() {
        return estoqueRepository.findEstoquesAbaixoDoNivelMinimo();
    }
    
    @Transactional(readOnly = true)
    public Estoque buscarEstoquePorProdutoELocal(Long idProduto, Long idLocalizacao) {
         return estoqueRepository.findByProdutoIdAndLocalizacaoId(idProduto, idLocalizacao)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado para o produto e localização."));
    }

    @Transactional
    public Estoque definirAlerta(Long idEstoque, EstoqueAlertaDto dto) {
        Estoque estoque = estoqueRepository.findById(idEstoque)
            .orElseThrow(() -> new ResourceNotFoundException("Item de estoque não encontrado."));
        estoque.setNivelMinimoAlerta(dto.nivelMinimoAlerta());
        return estoqueRepository.save(estoque);
    }

    @Transactional
    public void adicionarEstoque(Produto produto, Localizacao localizacao, int quantidade) {
        if (quantidade <= 0) {
            throw new BusinessRuleException("Quantidade de entrada deve ser positiva.");
        }

        Optional<Estoque> estoqueOpt = estoqueRepository.findByProdutoIdAndLocalizacaoId(produto.getId(), localizacao.getId());

        Estoque estoque;
        if (estoqueOpt.isPresent()) {
            estoque = estoqueOpt.get();
            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + quantidade);
        } else {
            estoque = Estoque.builder()
                    .produto(produto)
                    .localizacao(localizacao)
                    .quantidadeAtual(quantidade)
                    .nivelMinimoAlerta(0) // Define um padrão
                    .build();
        }
        estoqueRepository.save(estoque);
    }

    @Transactional
    public void removerEstoque(Produto produto, Localizacao localizacao, int quantidade) {
        if (quantidade <= 0) {
            throw new BusinessRuleException("Quantidade de saída deve ser positiva.");
        }

        Estoque estoque = estoqueRepository.findByProdutoIdAndLocalizacaoId(produto.getId(), localizacao.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Item não existe no estoque: " + produto.getNome()));

        if (estoque.getQuantidadeAtual() < quantidade) {
            throw new BusinessRuleException("Estoque insuficiente. Disponível: " + estoque.getQuantidadeAtual() + ", Solicitado: " + quantidade);
        }

        estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - quantidade);
        estoqueRepository.save(estoque);
    }
}