package com.mensal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "investimentos")
public class InvestimentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date data;

    @Column(nullable = true)
    private String descricao;

    @Column(columnDefinition = "float default 0")
    @Min(0)
    private float valor;

    @ManyToOne
    @JoinColumn(name = "carteira")
    @JsonIgnoreProperties({"receitas", "despesas", "caixa", "metas", "investimentos", "pessoas", "carteiras"})
    private CarteiraEntity carteira;

    @ManyToOne
    @JoinColumn(name = "metas")
    @JsonIgnoreProperties({"receitas", "despesas", "caixa", "investimentos", "pessoas", "carteiras"})
    private MetaEntity metas;
}
