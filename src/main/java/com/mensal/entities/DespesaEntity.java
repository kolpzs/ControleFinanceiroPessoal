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
@Entity(name = "despesas")
public class DespesaEntity {

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
    @JsonIgnoreProperties("despesas")
    private CarteiraEntity carteira;

    @ManyToMany
    @JoinTable(
            name = "despesa_tipo",
            joinColumns = @JoinColumn(name = "despesa_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_id")
    )
    @JsonIgnoreProperties("despesas")
    private List<TipoEntity> tipos;

}
