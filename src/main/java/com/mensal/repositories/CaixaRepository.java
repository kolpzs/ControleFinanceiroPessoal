package com.mensal.repositories;

import com.mensal.entities.CaixaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CaixaRepository extends JpaRepository<CaixaEntity,Long> {

}
