package com.mensal.services;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.MetaEntity;
import com.mensal.repositories.CarteiraRepository;
import com.mensal.repositories.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    public MetaEntity save(MetaEntity meta) {
        CarteiraEntity carteira = carteiraRepository.findById(meta.getCarteira().getId()).orElse(null);
        return metaRepository.save(meta);
    }

    public List<MetaEntity> findAllByCarteira(CarteiraEntity carteira) {
        return metaRepository.findAllByCarteira(carteira);
    }

    public MetaEntity findById(Long id) {
        return metaRepository.findById(id).orElseThrow();
    }

    public MetaEntity edit(MetaEntity meta) {
        MetaEntity metaBase = findById(meta.getId());

        if(metaBase != null) {
            if(meta.getNome() != null) {
                metaBase.setNome(meta.getNome());
            }
            if(meta.getDescricao() != null) {
                metaBase.setDescricao(meta.getDescricao());
            }
            if(meta.getValor() != 0 && meta.getValor() > 0) {
                metaBase.setValor(meta.getValor());
            }
            if(meta.getData_final() != null) {
                metaBase.setData_final(meta.getData_final());
            }
            if(meta.getData_inicial() != null) {
                metaBase.setData_inicial(meta.getData_inicial());
            }
            return metaRepository.save(metaBase);
        }
        return null;
    }

    public void delete(Long id) {
        MetaEntity meta = findById(id);
        metaRepository.delete(meta);
    }
}
