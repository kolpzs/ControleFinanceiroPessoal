package com.mensal.services;

import com.mensal.entities.DespesaEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.entities.TipoEntity;
import com.mensal.repositories.DespesaRepository;
import com.mensal.repositories.ReceitaRepository;
import com.mensal.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    public TipoEntity save(TipoEntity tipo) {
        return tipoRepository.save(tipo);
    }

    public List<TipoEntity> findAllTipos() {
        return tipoRepository.findAllTipos();
    }

    public TipoEntity findById(Long id) {
        return tipoRepository.findById(id).orElseThrow();
    }

    public float totalDespesaTipo(Long id) {
        TipoEntity tipo = findById(id);
        float somaDespesa = 0;
        for (DespesaEntity despesa : despesaRepository.findAll()) {
            if (despesa.getTipos().contains(tipo)) {
                somaDespesa += despesa.getValor();
            }
        }
        return somaDespesa;
    }

    public float totalReceitaTipo(Long id) {
        TipoEntity tipo = findById(id);
        float somaReceita = 0;
        for (ReceitaEntity receita : receitaRepository.findAll()) {
            if (receita.getTipos().contains(tipo)) {
                somaReceita += receita.getValor();
            }
        }
        return somaReceita;
    }

    public String totalTipo(Long id) {
        TipoEntity tipo = findById(id);
        String despesaTotal = "Total despesa: R$ " + totalDespesaTipo(id);
        String receitaTotal = "Total receita: R$ " + totalReceitaTipo(id);

        return despesaTotal + System.lineSeparator() + receitaTotal;
    }

    public List<TipoEntity> findByNome(String name) {
        return tipoRepository.findByNome(name);
    }
}
