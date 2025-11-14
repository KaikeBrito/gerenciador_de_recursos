package com.example.gerenciamento_lar_francisco_de_assis.controller;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.LocalizacaoDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import com.example.gerenciamento_lar_francisco_de_assis.service.LocalizacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/localizacoes")
@RequiredArgsConstructor
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;

    @PostMapping
    public ResponseEntity<Localizacao> criarLocalizacao(@Valid @RequestBody LocalizacaoDto dto) {
        Localizacao novaLocalizacao = localizacaoService.criar(dto);
        return new ResponseEntity<>(novaLocalizacao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Localizacao>> listarLocalizacoes() {
        return ResponseEntity.ok(localizacaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localizacao> buscarLocalizacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(localizacaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Localizacao> atualizarLocalizacao(
            @PathVariable Long id,
            @Valid @RequestBody LocalizacaoDto dto) {
        return ResponseEntity.ok(localizacaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLocalizacao(@PathVariable Long id) {
        localizacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}