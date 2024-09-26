package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.InvestimentoEntity;
import com.mensal.services.CarteiraService;
import com.mensal.services.InvestimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/save")
    public ResponseEntity<InvestimentoEntity> save(@RequestBody InvestimentoEntity investimento) {
        InvestimentoEntity savedInvestimento = investimentoService.save(investimento);
        return ResponseEntity.ok(savedInvestimento);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<InvestimentoEntity> findById(@PathVariable Long id) {
        try {
            InvestimentoEntity investimento = investimentoService.findById(id);
            return ResponseEntity.ok(investimento);
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllByCarteira/{id}")
    public ResponseEntity<List<InvestimentoEntity>> findAllByCarteira(@PathVariable Long id) {
        try {
            CarteiraEntity carteira = carteiraService.findById(id);
            List<InvestimentoEntity> investimentos = investimentoService.findAllByCarteira(carteira);
            return ResponseEntity.ok(investimentos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<InvestimentoEntity> edit(@RequestBody InvestimentoEntity investimento) {
        if (investimento != null) {
            InvestimentoEntity updatedInvestimento = investimentoService.edit(investimento);
            return ResponseEntity.ok(updatedInvestimento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
