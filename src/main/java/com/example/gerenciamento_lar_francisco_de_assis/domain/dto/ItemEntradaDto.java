package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record ItemEntradaDto(
        @NotNull Long idProduto,
        @NotNull Long idLocalizacaoDestino,
        @NotNull @Positive Integer quantidade,
        LocalDate dataValidade
) {}