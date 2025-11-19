package com.example.gerenciamento_lar_francisco_de_assis.domain.respository;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {

    Optional<Doador> findByDocumento(String documento);

    List<Doador> findByNomeContainingIgnoreCase(String nome);
}