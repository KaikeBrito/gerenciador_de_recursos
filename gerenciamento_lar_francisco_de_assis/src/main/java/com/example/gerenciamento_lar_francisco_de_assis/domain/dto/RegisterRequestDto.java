package com.example.gerenciamento_lar_francisco_de_assis.domain.dto;

import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.PapelUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 255)
        String nomeCompleto,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Size(max = 255)
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
        String senha,
        
        PapelUsuario papel
) {}