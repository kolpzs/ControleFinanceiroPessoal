package com.mensal.repositories;

import com.mensal.entities.TipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRepository extends JpaRepository<TipoEntity,Long> {

    @Query(value = "SELECT id, tipo FROM tipos", nativeQuery = true)
    List<TipoEntity> findAllTipos();

    @Query(value = "SELECT id, tipo FROM tipos WHERE tipo = :nome", nativeQuery = true)
    List<TipoEntity> findByNome(String nome);
}
