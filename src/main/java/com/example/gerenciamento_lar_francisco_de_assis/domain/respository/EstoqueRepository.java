package com.example.gerenciamento_lar_francisco_de_assis.domain.respository;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Estoque;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByProdutoIdAndLocalizacaoId(Long idProduto, Long idLocalizacao);

    List<Estoque> findByProdutoId(Long idProduto);

    @Query("SELECT SUM(e.quantidadeAtual) FROM Estoque e WHERE e.produto.id = :idProduto")
    Optional<Integer> getQuantidadeTotalPorProduto(Long idProduto);

    @Query("SELECT e FROM Estoque e WHERE e.quantidadeAtual <= e.nivelMinimoAlerta")
    List<Estoque> findEstoquesAbaixoDoNivelMinimo();

	int countByLocalizacao(Localizacao localizacao);

	int countByProduto(Produto produto);
}