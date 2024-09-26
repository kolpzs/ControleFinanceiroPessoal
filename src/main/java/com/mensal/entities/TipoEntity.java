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
@Entity(name = "tipos")
public class TipoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @ManyToMany(mappedBy = "tipos")
    @JsonIgnoreProperties({"receitas", "despesas"})
    private List<DespesaEntity> despesas;

    @ManyToMany(mappedBy = "tipos")
    @JsonIgnoreProperties({"receitas", "despesas"})
    private List<ReceitaEntity> receitas;

}
