package com.mensal.repositories;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.MetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<MetaEntity,Long> {

    @Query("SELECT m FROM metas m WHERE m.carteira = :carteira")
    List<MetaEntity> findAllByCarteira(@Param("carteira") CarteiraEntity carteira);
}
