package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String nome; // Ex: "Fralda Geriátrica G - Pacote 20 un"

    @Size(max = 500)
    private String descricao;

    @NotNull
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String unidadeMedida; // Ex: "UN", "PCT", "KG", "L"

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    @ToString.Exclude
    private Categoria categoria;
    
    @OneToMany(mappedBy = "produto")
    @ToString.Exclude
    private List<Estoque> estoques;

    @OneToMany(mappedBy = "produto")
    @ToString.Exclude
    private List<ItemEntrada> itensEntrada;

    @OneToMany(mappedBy = "produto")
    @ToString.Exclude
    private List<ItemSaida> itensSaida;
}