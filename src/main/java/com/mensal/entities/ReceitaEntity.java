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
@Entity(name = "receitas")
public class ReceitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date data;

    @Column(nullable = false)
    private String descricao;

    @Column(columnDefinition = "float default 0")
    @Min(0)
    private float valor;

    @ManyToOne
    @JoinColumn(name = "carteira")
    @JsonIgnoreProperties({"receitas", "despesas", "caixa", "metas", "investimentos", "pessoas", "carteiras"})
    private CarteiraEntity carteira;

    @ManyToMany
    @JoinTable(
            name = "receita_tipo",
            joinColumns = @JoinColumn(name = "receita_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_id")
    )
    @JsonIgnoreProperties("receitas")
    private List<TipoEntity> tipos;

}
