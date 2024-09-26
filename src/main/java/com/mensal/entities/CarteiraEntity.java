package com.mensal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "carteiras")
public class CarteiraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    @JsonIgnoreProperties({"carteira", "pessoas"})
    private PessoaEntity pessoa;

    @OneToOne
    @JoinColumn(name = "caixa_id")
    @JsonIgnoreProperties("carteira")
    private CaixaEntity caixa;

    @OneToMany(mappedBy = "carteira")
    @JsonIgnoreProperties("carteiras")
    private List<DespesaEntity> despesas;

    @OneToMany(mappedBy = "carteira")
    @JsonIgnoreProperties("carteiras")
    private List<ReceitaEntity> receitas;

    @OneToMany(mappedBy = "carteira")
    @JsonIgnoreProperties("carteiras")
    private List<MetaEntity> metas;

    @OneToMany(mappedBy = "carteira")
    @JsonIgnoreProperties("carteiras")
    private List<InvestimentoEntity> investimentos;

}
