package com.example.gerenciamento_lar_francisco_de_assis.domain.respository;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.ItemEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemEntradaRepository extends JpaRepository<ItemEntrada, Long> {

    List<ItemEntrada> findByProdutoId(Long idProduto);
    
    List<ItemEntrada> findByEntradaId(Long idEntrada);

    @Query("SELECT ie FROM ItemEntrada ie WHERE ie.dataValidade IS NOT NULL AND ie.dataValidade <= :dataLimite ORDER BY ie.dataValidade ASC")
    List<ItemEntrada> findItensProximosDaValidade(LocalDate dataLimite);
}