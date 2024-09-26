package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.services.CarteiraService;
import com.mensal.services.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @PostMapping("/save")
    public ResponseEntity<ReceitaEntity> save(@RequestBody ReceitaEntity receita) {
        return ResponseEntity.ok(receitaService.save(receita));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ReceitaEntity> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(receitaService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllByCarteira/{id}")
    public ResponseEntity<List<ReceitaEntity>> findAllByCarteira(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(receitaService.findAllByCarteira(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<ReceitaEntity> edit(@RequestBody ReceitaEntity receita) {
        try {
            return ResponseEntity.ok(receitaService.edit(receita));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
