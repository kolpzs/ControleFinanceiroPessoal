package com.mensal.repositories;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<DespesaEntity,Long> {

    @Query("SELECT d FROM despesas d WHERE d.carteira = :carteira")
    List<DespesaEntity> findAllByCarteira(@Param("carteira") CarteiraEntity carteira);
}
