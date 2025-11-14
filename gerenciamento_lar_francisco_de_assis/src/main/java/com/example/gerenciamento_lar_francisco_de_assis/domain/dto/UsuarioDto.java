package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.PapelUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDto(
        @NotNull @Size(min = 3, max = 255) String nomeCompleto,
        @Email @NotNull @Size(max = 255) String email,
        @NotNull @Size(min = 8) String senha,
        @NotNull PapelUsuario papel
) {}