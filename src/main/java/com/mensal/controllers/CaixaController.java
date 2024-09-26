package com.mensal.controllers;

import com.mensal.entities.CaixaEntity;
import com.mensal.services.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixas")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<CaixaEntity> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(caixaService.findById(id));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateValor/{id}")
    public ResponseEntity<CaixaEntity> updateValor(@PathVariable Long id, @RequestParam float valor) {
        try {
            return ResponseEntity.ok(caixaService.updateValor(id, valor));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }
}
