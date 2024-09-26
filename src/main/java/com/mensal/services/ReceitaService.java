package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    public ReceitaEntity save(ReceitaEntity receita) {
        updateCaixa(receita);
        return receitaRepository.save(receita);
    }

    public List<ReceitaEntity> findAllByCarteira(Long id) {
        CarteiraEntity carteira = carteiraRepository.findById(id).orElseThrow();
        return receitaRepository.findAllByCarteira(carteira);
    }

    public ReceitaEntity findById(Long id) {
        return receitaRepository.findById(id).orElseThrow();
    }

    public ReceitaEntity edit(ReceitaEntity receita) {
        ReceitaEntity receitaBase = findById(receita.getId());
        CaixaEntity caixa = receitaBase.getCarteira().getCaixa();
        float valorAtual;

        if (receitaBase != null) {
            if (receita.getData() != null) {
                receitaBase.setData(receita.getData());
            }
            if (receita.getDescricao() != null) {
                receitaBase.setDescricao(receita.getDescricao());
            }
            if (receita.getValor() >= 0) {
                valorAtual = caixa.getValor() + receita.getValor() - receitaBase.getValor();
                receitaBase.getCarteira().getCaixa().setValor(valorAtual);
                receitaBase.setValor(receita.getValor());
            }
            if (receita.getTipos() != null) {
                receitaBase.setTipos(receita.getTipos());
            }
            return receitaRepository.save(receitaBase);
        }
        return null;
    }

    private void updateCaixa(ReceitaEntity receita) {
        CarteiraEntity carteira = carteiraRepository.findById(receita.getCarteira().getId()).orElseThrow();
        CaixaEntity caixa = carteira.getCaixa();
        float novoSaldo = 0F;
        if (caixa != null) {
            novoSaldo = caixa.getValor() + receita.getValor();
            caixa.setValor(novoSaldo);
            caixaRepository.save(caixa);
        }
    }

    public List<ReceitaEntity> findAllByCarteiraOrderByDataAsc(CarteiraEntity carteira) {
        return receitaRepository.findAllByCarteiraOrderByDataAsc(carteira);
    }
}
