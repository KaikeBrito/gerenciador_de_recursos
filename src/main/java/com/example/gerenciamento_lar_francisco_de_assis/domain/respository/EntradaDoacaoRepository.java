package com.example.gerenciamento_lar_francisco_de_assis.domain.respository;

import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Doador;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.EntradaDoacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntradaDoacaoRepository extends JpaRepository<EntradaDoacao, Long> {

    List<EntradaDoacao> findByDoadorId(Long idDoador);

    List<EntradaDoacao> findByUsuarioRegistroId(Long idUsuario);

    List<EntradaDoacao> findByDataEntradaBetween(LocalDateTime inicio, LocalDateTime fim);

	int countByDoador(Doador doador);
}