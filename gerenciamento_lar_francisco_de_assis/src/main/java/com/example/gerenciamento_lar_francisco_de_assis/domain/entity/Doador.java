package com.example.gerenciamento_lar_francisco_de_assis.domain.entity;

import java.util.List;

import com.example.gerenciamento_lar_francisco_de_assis.domain.enums.TipoPessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doadores")
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private TipoPessoa tipoPessoa;

    // Pode ser CPF ou CNPJ
    @Size(max = 20)
    @Column(unique = true, length = 20) 
    private String documento;

    @Email
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "doador")
    @ToString.Exclude
    private List<EntradaDoacao> doacoes;
}
