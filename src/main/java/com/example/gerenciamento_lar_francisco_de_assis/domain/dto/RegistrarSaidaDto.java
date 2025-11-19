package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegistrarSaidaDto(
        @NotNull Long idUsuarioRegistro,
        @NotNull String destino,
        String justificativa,
        @NotNull List<ItemSaidaDto> itens
) {}