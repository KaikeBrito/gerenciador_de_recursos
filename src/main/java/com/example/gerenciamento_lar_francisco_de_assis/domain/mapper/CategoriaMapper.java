package com.example.gerenciamento_lar_francisco_de_assis.domain.mapper;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.CategoriaDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaDto dto, Categoria entity) {
        if (entity == null) {
            entity = new Categoria();
        }
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        return entity;
    }
}
