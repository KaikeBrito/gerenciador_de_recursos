package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoDto(
        @NotNull @Size(min = 3, max = 255) String nome,
        @Size(max = 500) String descricao,
        @NotNull @Size(max = 10) String unidadeMedida,
        @NotNull Long idCategoria
) {}