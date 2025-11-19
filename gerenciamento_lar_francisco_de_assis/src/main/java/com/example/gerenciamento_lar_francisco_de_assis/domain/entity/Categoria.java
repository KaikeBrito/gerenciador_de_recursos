package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Size(max = 255)
    private String descricao;
    
    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    private List<Produto> produtos;
}