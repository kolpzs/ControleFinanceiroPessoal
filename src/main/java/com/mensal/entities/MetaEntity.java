package com.mensal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "metas")
public class MetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = true)
    private String descricao;

    @Column(columnDefinition = "float default 0")
    @Min(0)
    private float valor;

    @Column(nullable = false)
    private Date data_inicial;

    @Column(nullable = false)
    private Date data_final;

    @Column(nullable = false, columnDefinition = "float default 0")
    @Min(0)
    private float completo;

    @OneToMany(mappedBy = "meta")
    @JsonIgnoreProperties({"receitas", "despesas", "metas", "caixa", "carteiras"})
    private List<InvestimentoEntity> investimentos;

    @ManyToOne
    @JoinColumn(name = "carteira_id")
    @JsonIgnoreProperties({"receitas", "despesas", "metas", "caixa", "carteiras", "investimentos", "pessoas", "carteiras"})
    private CarteiraEntity carteira;

}
