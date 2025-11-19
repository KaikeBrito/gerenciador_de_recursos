package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estoque",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_produto", "id_localizacao"})
    }
)
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_localizacao", nullable = false)
    private Localizacao localizacao;

    @NotNull
    @Column(nullable = false)
    private Integer quantidadeAtual;

    private Integer nivelMinimoAlerta;

    private LocalDate dataValidade;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime ultimaAtualizacao;
}