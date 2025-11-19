package com.example.gerenciamento_lar_francisco_de_assis.domain.mapper;

import com.example.gerenciamento_lar_francisco_de_assis.domain.dto.DoadorDto;
import com.example.gerenciamento_lar_francisco_de_assis.domain.entity.Doador;
import org.springframework.stereotype.Component;

@Component
public class DoadorMapper {

    public Doador toEntity(DoadorDto dto, Doador entity) {
        if (entity == null) {
            entity = new Doador();
        }
        entity.setNome(dto.nome());
        entity.setTipoPessoa(dto.tipoPessoa());
        entity.setDocumento(dto.documento());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        return entity;
    }
}