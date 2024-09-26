package com.mensal.controllers;

import com.mensal.entities.TipoEntity;
import com.mensal.services.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @PostMapping("/save")
    public ResponseEntity<TipoEntity> save(@RequestBody TipoEntity tipo) {
        TipoEntity savedTipo = tipoService.save(tipo);
        return ResponseEntity.ok(savedTipo);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<TipoEntity> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(tipoService.findById(id));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllTipos")
    public ResponseEntity<List<TipoEntity>> findAllTipos(){
        try{
            return ResponseEntity.ok(tipoService.findAllTipos());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/totalTipo/{id}")
    public ResponseEntity<String> totalTipo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tipoService.totalTipo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<TipoEntity>> findByNome(@PathVariable String nome) {
        try {
            return ResponseEntity.ok(tipoService.findByNome(nome));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
