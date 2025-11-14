package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.TipoPessoa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoadorDto(
        @NotNull @Size(min = 3, max = 255) String nome,
        @NotNull TipoPessoa tipoPessoa,
        @Size(max = 20) String documento, // CPF/CNPJ
        @Email @Size(max = 255) String email,
        @Size(max = 20) String telefone
) {}