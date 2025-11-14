package com.example.gerenciamento_lar_francisco_de_assis.domain.respository;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.ItemSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemSaidaRepository extends JpaRepository<ItemSaida, Long> {

    List<ItemSaida> findByProdutoId(Long idProduto);

    List<ItemSaida> findBySaidaId(Long idSaida);

    @Query("SELECT SUM(is.quantidade) FROM ItemSaida is " +
           "WHERE is.produto.id = :idProduto " +
           "AND is.saida.dataSaida BETWEEN :inicio AND :fim")
    Optional<Integer> getConsumoTotalPorProdutoNoPeriodo(Long idProduto, LocalDateTime inicio, LocalDateTime fim);
}