package com.example.gerenciamento_lar_francisco_de_assis.domain.respository;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.SaidaRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaidaRecursoRepository extends JpaRepository<SaidaRecurso, Long> {

    List<SaidaRecurso> findByUsuarioRegistroId(Long idUsuario);

    List<SaidaRecurso> findByDestinoContainingIgnoreCase(String destino);

    List<SaidaRecurso> findByDataSaidaBetween(LocalDateTime inicio, LocalDateTime fim);
}