package com.example.gerenciamento_lar_francisco_de_assis.domain.mapper;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.LocalizacaoDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Localizacao;
import org.springframework.stereotype.Component;

@Component
public class LocalizacaoMapper {

    public Localizacao toEntity(LocalizacaoDto dto, Localizacao entity) {
        if (entity == null) {
            entity = new Localizacao();
        }
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        return entity;
    }
}