package com.mensal.repositories;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.InvestimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<InvestimentoEntity,Long> {

    @Query("SELECT i FROM investimentos i WHERE i.carteira = :carteira")
    List<InvestimentoEntity> findAllByCarteira(@Param("carteira") CarteiraEntity carteira);
}
