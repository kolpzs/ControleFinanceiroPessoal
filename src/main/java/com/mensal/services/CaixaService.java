package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.repositories.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    public CaixaEntity save(CaixaEntity caixa) {
        return caixaRepository.save(caixa);
    }

    public CaixaEntity findById(Long id) {
        return caixaRepository.findById(id).orElseThrow();
    }

    public CaixaEntity updateValor(Long id, float novoValor) {
        CaixaEntity caixa = caixaRepository.findById(id).orElseThrow();
        caixa.setValor(novoValor);
        return caixaRepository.save(caixa);
    }
}
