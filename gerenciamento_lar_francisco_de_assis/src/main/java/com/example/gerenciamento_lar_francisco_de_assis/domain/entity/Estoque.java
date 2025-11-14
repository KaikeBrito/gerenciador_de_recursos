package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estoque",
    // Garante que só existe um registro de estoque por produto/localização
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_produto", "id_localizacao"})
    }
)
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    @ToString.Exclude
    private Produto produto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localizacao", nullable = false)
    @ToString.Exclude
    private Localizacao localizacao;

    @NotNull
    @Column(nullable = false)
    private Integer quantidadeAtual;

    private Integer nivelMinimoAlerta;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime ultimaAtualizacao;
}