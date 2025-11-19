package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "saidas_recurso")
public class SaidaRecurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario_registro", nullable = false)
    @ToString.Exclude
    private Usuario usuarioRegistro;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataSaida;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String destino; // Ex: "Cozinha", "Ala B", "Consumo Interno"

    @Size(max = 500)
    private String justificativa;

    // Se a "Saída" for deletada, os itens dela também são.
    @JsonIgnore
    @OneToMany(mappedBy = "saida", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ItemSaida> itens;
}