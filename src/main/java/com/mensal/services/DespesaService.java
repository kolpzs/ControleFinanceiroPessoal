package com.mensal.services;

import com.mensal.entities.*;
import com.mensal.entities.DespesaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    public DespesaEntity save(DespesaEntity despesa) {
        updateCaixa(despesa);
        return despesaRepository.save(despesa);
    }

    public List<DespesaEntity> findAllByCarteira(Long id) {
        CarteiraEntity carteira = carteiraRepository.findById(id).orElseThrow();
        return despesaRepository.findAllByCarteira(carteira);
    }

    public DespesaEntity findById(Long id) {
        return despesaRepository.findById(id).orElseThrow();
    }

    public DespesaEntity edit(DespesaEntity despesa) {
        DespesaEntity despesaBase = findById(despesa.getId());
        CaixaEntity caixa = despesaBase.getCarteira().getCaixa();
        float valorAtual;

        if (despesaBase != null) {
            if (despesa.getData() != null) {
                despesaBase.setData(despesa.getData());
            }
            if (despesa.getDescricao() != null) {
                despesaBase.setDescricao(despesa.getDescricao());
            }
            if (despesa.getValor() >= 0) {
                valorAtual = caixa.getValor() - despesa.getValor() + despesaBase.getValor();
                despesaBase.getCarteira().getCaixa().setValor(valorAtual);
                despesaBase.setValor(despesa.getValor());
            }
            if (despesa.getTipos() != null) {
                despesaBase.setTipos(despesa.getTipos());
            }
            return despesaRepository.save(despesaBase);
        }
        return null;
    }

    private void updateCaixa(DespesaEntity despesa) {
        CaixaEntity caixa = despesa.getCarteira().getCaixa();
        float novoSaldo = 0F;
        if (caixa != null && caixa.getId() != null && despesa.getValor() <= caixa.getValor()) {
            novoSaldo = caixa.getValor() - despesa.getValor();
            caixa.setValor(novoSaldo);
            caixaRepository.save(caixa);
        }
    }
}
