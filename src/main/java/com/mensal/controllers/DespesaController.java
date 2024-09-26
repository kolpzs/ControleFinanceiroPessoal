package com.mensal.controllers;

import com.mensal.entities.DespesaEntity;
import com.mensal.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @PostMapping("/save")
    public ResponseEntity<DespesaEntity> save(@RequestBody DespesaEntity despesa) {
        return ResponseEntity.ok(despesaService.save(despesa));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<DespesaEntity> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(despesaService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllByCarteira/{id}")
    public ResponseEntity<List<DespesaEntity>> findAllByCarteira(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(despesaService.findAllByCarteira(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<DespesaEntity> edit(@RequestBody DespesaEntity despesa) {
        try {
            return ResponseEntity.ok(despesaService.edit(despesa));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
