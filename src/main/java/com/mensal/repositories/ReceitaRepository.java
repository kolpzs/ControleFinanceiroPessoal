package com.mensal.repositories;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaEntity,Long> {

    @Query("SELECT r FROM receitas r WHERE r.carteira = :carteira")
    List<ReceitaEntity> findAllByCarteira(@Param("carteira") CarteiraEntity carteira);

    @Query("SELECT r FROM receitas r WHERE r.carteira = :carteira ORDER BY r.data ASC")
    List<ReceitaEntity> findAllByCarteiraOrderByDataAsc(@Param("carteira") CarteiraEntity carteira);
}