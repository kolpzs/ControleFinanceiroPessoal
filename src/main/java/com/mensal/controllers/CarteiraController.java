package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.DespesaEntity;
import com.mensal.entities.ReceitaEntity;
import com.mensal.services.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/save")
    public ResponseEntity<CarteiraEntity> save(@RequestBody CarteiraEntity carteira, @RequestParam Long id ) {
        CarteiraEntity savedCarteira = carteiraService.save(carteira, id);
        return ResponseEntity.ok(savedCarteira);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<CarteiraEntity> findById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(carteiraService.findById(id));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listDespesasTotal")
    public ResponseEntity<List<DespesaEntity>> listDespesas(@RequestParam Long id){
        try{
            return ResponseEntity.ok(carteiraService.listDespesasTotal(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listReceitasTotal")
    public ResponseEntity<List<ReceitaEntity>> listReceitas(@RequestParam Long id){
        try{
            return ResponseEntity.ok(carteiraService.listReceitasTotal(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getCaixaSaldo")
    public ResponseEntity<Float> getCaixaSaldo(@RequestParam Long id){
        try{
            return ResponseEntity.ok(carteiraService.getCaixaSaldo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/relatorioMensal")
    public ResponseEntity<String> relatorioMensal(@RequestParam Long id){
        try {
            return ResponseEntity.ok(carteiraService.relatorioMensal(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
