package com.mensal.services;

import com.mensal.entities.*;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.InvestimentoRepository;
import com.mensal.repositories.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentosRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    public InvestimentoEntity save(InvestimentoEntity investimento) {
        updateCaixa(investimento);
        updateMeta(investimento);
        return investimentosRepository.save(investimento);
    }

    public List<InvestimentoEntity> findAllByCarteira(CarteiraEntity carteira) {
        return investimentosRepository.findAllByCarteira(carteira);
    }

    public InvestimentoEntity findById(Long id) {
        return investimentosRepository.findById(id).orElseThrow();
    }

    public InvestimentoEntity edit(InvestimentoEntity investimentos) {
        InvestimentoEntity investimentosBase = findById(investimentos.getId());
        MetaEntity meta = investimentosBase.getMetas();
        CaixaEntity caixa = meta.getCarteira().getCaixa();
        float caixaAtual;
        float valorAtual;

        if(investimentosBase != null) {
            if(investimentos.getData() != null) {
                investimentosBase.setData(investimentos.getData());
            }
            if(investimentos.getDescricao() != null) {
                investimentosBase.setDescricao(investimentos.getDescricao());
            }
            if(investimentos.getValor() != 0 && investimentos.getValor() > 0) {
                valorAtual = meta.getCompleto() - investimentosBase.getValor() + investimentos.getValor();
                caixaAtual = caixa.getValor() - investimentosBase.getValor() + investimentos.getValor();
                investimentosBase.getMetas().setCompleto(valorAtual);
                caixa.setValor(caixaAtual);
                investimentosBase.setValor(investimentos.getValor());
            }
            return investimentosRepository.save(investimentosBase);
        }
        return null;
    }

    private void updateCaixa(InvestimentoEntity investimento) {
        CarteiraEntity carteira = carteiraRepository.findById(investimento.getCarteira().getId()).orElseThrow();
        CaixaEntity caixa = carteira.getCaixa();
        float novoSaldo;
        if(caixa != null && investimento.getValor() < caixa.getValor()) {
            novoSaldo = caixa.getValor() - investimento.getValor();
            caixa.setValor(novoSaldo);
            caixaRepository.save(caixa);
        }
    }

    private void updateMeta(InvestimentoEntity investimento) {
        CarteiraEntity carteira = investimento.getCarteira();
        List<MetaEntity> metas = carteira.getMetas();
        float novoSaldo;
        for(MetaEntity meta : metas) {
            if(meta != null && Objects.equals(investimento.getMetas().getId(), meta.getId())) {
                novoSaldo = meta.getCompleto() + investimento.getValor();
                meta.setCompleto(novoSaldo);
                metaRepository.save(meta);
                break;
            }
        }
    }
}
