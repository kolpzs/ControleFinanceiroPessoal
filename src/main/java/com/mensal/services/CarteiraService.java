package com.mensal.services;

import com.mensal.entities.*;
import com.mensal.repositories.CaixaRepository;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public CarteiraEntity save(CarteiraEntity carteira, Long id) {
        PessoaEntity pessoa = pessoaRepository.findById(id).orElseThrow();
        carteira.setPessoa(pessoa);
        CarteiraEntity novaCarteira = carteiraRepository.save(carteira);

        CaixaEntity novoCaixa = new CaixaEntity();
        novoCaixa.setConta("Caixa de " + pessoa.getNome());
        novoCaixa.setValor(0);
        pessoa.setCarteira(novaCarteira);
        novoCaixa.setCarteira(novaCarteira);
        novaCarteira.setCaixa(novoCaixa);
        caixaRepository.save(novoCaixa);

        return novaCarteira;
    }

    public CarteiraEntity findById(Long id) {
        return carteiraRepository.findById(id).orElseThrow();
    }

    public List<DespesaEntity> listDespesasTotal(Long id) {
        CarteiraEntity carteira = findById(id);
        return carteiraRepository.listDespesasTotal(carteira);
    }

    public List<ReceitaEntity> listReceitasTotal(Long id) {
        CarteiraEntity carteira = findById(id);
        return carteiraRepository.listReceitasTotal(carteira);
    }

    public float getCaixaSaldo(Long id) {
        CarteiraEntity carteira = findById(id);
        return carteira.getCaixa().getValor();
    }

    public List<DespesaEntity> listDespesasMensal(Long id) {
        CarteiraEntity carteira = findById(id);
        Date today = new Date();
        return carteiraRepository.listDespesasMensal(today, carteira);
    }

    public List<ReceitaEntity> listReceitasMensal(Long id) {
        CarteiraEntity carteira = findById(id);
        Date today = new Date();
        return carteiraRepository.listReceitasMensal(today, carteira);
    }

    public float somaDespesasMensal(Long id) {
        List<DespesaEntity> despesas = listDespesasMensal(id);
        float soma = 0;
        for (DespesaEntity despesa : despesas) {
            soma += despesa.getValor();
        }
        return soma;
    }

    public float somaReceitasMensal(Long id) {
        List<ReceitaEntity> receitas = listReceitasMensal(id);
        float soma = 0;
        for (ReceitaEntity receita : receitas) {
            soma += receita.getValor();
        }
        return soma;
    }

    public String relatorioMensal(Long id) {
        CarteiraEntity carteira = findById(id);

        return "Relatório Mensal\n" +
                "Carteira: " + carteira.getNome() + "\n" +
                "Total de Receitas: R$ " + String.format("%.2f", somaReceitasMensal(id)) + "\n" +
                "Total de Despesas: R$ " + String.format("%.2f", somaDespesasMensal(id)) + "\n" +
                "Saldo do Caixa: R$ " + String.format("%.2f", carteira.getCaixa().getValor()) + "\n" +
                "Metas: " + relatorioMetas(id);
    }

    public List<String> relatorioMetas(Long id) {
        List<MetaEntity> metas = findById(id).getMetas();
        if (metas == null) {
            metas = Collections.emptyList();
        }

        List<String> metasRetorno = new ArrayList<>();
        int i = 0;
        for (MetaEntity meta : metas) {
            String texto = String.format(
                    "%d - Meta %s\n" +
                            "Descrição: %s\n" +
                            "Total: R$ %.2f\n" +
                            "Restante: R$ %.2f\n" +
                            "Data final: %s - Dias Restantes: %d\n\n",
                    i, meta.getNome(), meta.getDescricao(), meta.getValor(),
                    meta.getValor() - meta.getCompleto(), meta.getData_final(),
                    (new Date().getTime() - meta.getData_final().getTime()) / (1000 * 60 * 60 * 24)
            );
            metasRetorno.add(texto);
            i++;
        }
        return metasRetorno;
    }

}
