package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO para criar ou atualizar uma Categoria
public record CategoriaDto(
        @NotNull @Size(min = 3, max = 100) String nome,
        @Size(max = 255) String descricao
) {}