package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.PapelUsuario;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String nomeCompleto;

    @Email
    @NotNull
    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 8) // Armazene apenas o hash, não a senha pura
    @Column(nullable = false)
    private String senha;
    
    @JsonIgnore
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PapelUsuario papel;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioRegistro")
    @ToString.Exclude
    private List<EntradaDoacao> entradasRegistradas;

    @JsonIgnore
    @OneToMany(mappedBy = "usuarioRegistro")
    @ToString.Exclude
    private List<SaidaRecurso> saidasRegistradas;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;
}