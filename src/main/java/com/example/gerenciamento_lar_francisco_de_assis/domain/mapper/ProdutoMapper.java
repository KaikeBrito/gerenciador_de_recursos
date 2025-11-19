package com.example.gerenciamento_lar_francisco_de_assis.domain.mapper;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.ProdutoDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoDto dto, Produto entity) {
        if (entity == null) {
            entity = new Produto();
        }
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setUnidadeMedida(dto.unidadeMedida());
        return entity;
    }
}