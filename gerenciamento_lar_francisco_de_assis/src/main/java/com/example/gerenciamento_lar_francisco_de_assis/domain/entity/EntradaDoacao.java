package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "entradas_doacao")
public class EntradaDoacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doador") // Pode ser nulo (doação anônima)
    @ToString.Exclude
    private Doador doador;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_registro", nullable = false)
    @ToString.Exclude
    private Usuario usuarioRegistro;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEntrada;

    @Size(max = 255)
    private String notaFiscal;

    @Size(max = 500)
    private String observacoes;

    // Se a "Entrada" for deletada, os itens dela também são.
    @OneToMany(mappedBy = "entrada", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ItemEntrada> itens;
}