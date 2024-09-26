package com.mensal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "caixas")
public class CaixaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String conta;

    @Column(columnDefinition = "float default 0")
    @Min(0)
    private float valor;

    @OneToOne(mappedBy = "caixa")
    @JsonIgnoreProperties({"caixas", "carteiras"})
    private CarteiraEntity carteira;

}
