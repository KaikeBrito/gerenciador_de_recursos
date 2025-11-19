package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RegistrarEntradaDto(
        Long idDoador, // Pode ser nulo
        @NotNull Long idUsuarioRegistro,
        String observacoes,
        @NotNull List<ItemEntradaDto> itens
) {}