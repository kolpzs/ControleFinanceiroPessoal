package com.mensal.services;

import com.mensal.entities.CaixaEntity;
import com.mensal.entities.PessoaEntity;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    public PessoaEntity save(PessoaEntity pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<PessoaEntity> findAll() {
        return pessoaRepository.findAll();
    }

    public PessoaEntity findById(Long id) {
        return pessoaRepository.findById(id).orElseThrow();
    }

    public PessoaEntity edit(PessoaEntity pesssoa) {
        PessoaEntity pessoaBase = findById(pesssoa.getId());
        if(pessoaBase != null) {
            if(pesssoa.getNome() != null) {
                if(pessoaBase.getCarteira() != null) {
                    CaixaEntity caixa = pessoaBase.getCarteira().getCaixa();
                    caixa.setConta("Caixa de " + pesssoa.getNome());
                }
                pessoaBase.setNome(pesssoa.getNome());
            }
            if(pesssoa.getEmail() != null) {
                pessoaBase.setEmail(pesssoa.getEmail());
            }
            if(pesssoa.getTelefone() != null) {
                pessoaBase.setTelefone(pesssoa.getTelefone());
            }
            return pessoaRepository.save(pessoaBase);
        }
        return null;
    }
}
