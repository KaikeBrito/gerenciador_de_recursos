package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemSaidaDto(
        @NotNull Long idProduto,
        @NotNull Long idLocalizacaoOrigem,
        @NotNull @Positive Integer quantidade
) {}