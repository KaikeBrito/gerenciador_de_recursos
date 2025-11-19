package com.example.gerenciamento_lar_francisco_de_assis.domain.mapper;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.UsuarioDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
    public Usuario toEntity(UsuarioDto dto, Usuario entity) {
        if (entity == null) {
            entity = new Usuario();
        }
        entity.setNomeCompleto(dto.nomeCompleto());
        entity.setEmail(dto.email());
        entity.setPapel(dto.papel());
        return entity;
    }
}