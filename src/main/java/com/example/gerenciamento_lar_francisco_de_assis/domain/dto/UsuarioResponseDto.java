package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.PapelUsuario;

public record UsuarioResponseDto(
        Long id,
        String nomeCompleto,
        String email,
        PapelUsuario papel
) {}