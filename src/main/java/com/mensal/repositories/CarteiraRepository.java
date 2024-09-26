package com.mensal.repositories;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import com.mensal.entities.MetaEntity;
import com.mensal.entities.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarteiraRepository extends JpaRepository<CarteiraEntity,Long> {

    @Query("SELECT d FROM despesas d WHERE d.carteira = :carteira")
    List<DespesaEntity> listDespesasTotal(CarteiraEntity carteira);

    @Query("SELECT r FROM receitas r WHERE r.carteira = :carteira")
    List<ReceitaEntity> listReceitasTotal(CarteiraEntity carteira);

    @Query("SELECT r FROM receitas r WHERE MONTH(r.data) = MONTH(:mesAtual) AND YEAR(r.data) = YEAR(:mesAtual) AND r.carteira = :carteira")
    List<ReceitaEntity> listReceitasMensal(@Param("mesAtual") Date mesAtual, @Param("carteira") CarteiraEntity carteira);

    @Query("SELECT d FROM despesas d WHERE MONTH(d.data) = MONTH(:mesAtual) AND YEAR(d.data) = YEAR(:mesAtual) AND d.carteira = :carteira")
    List<DespesaEntity> listDespesasMensal(@Param("mesAtual") Date mesAtual, @Param("carteira") CarteiraEntity carteira);

    @Query("SELECT m FROM metas m WHERE m.carteira = :carteira")
    List<MetaEntity> listMetas(CarteiraEntity carteira);
}
