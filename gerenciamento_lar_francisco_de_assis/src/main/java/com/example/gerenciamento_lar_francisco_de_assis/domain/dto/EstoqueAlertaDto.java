package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;

public record EstoqueAlertaDto(
    @NotNull Integer nivelMinimoAlerta
) {}