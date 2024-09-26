package com.mensal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "pessoas")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-\\d{4}$")
    private String telefone;

    @Column(nullable = true)
    @Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$")
    private String email;

    @OneToOne(mappedBy = "pessoa")
    @JsonIgnoreProperties("pessoa")
    private CarteiraEntity carteira;
}
