package com.mensal.controllers;

import com.mensal.entities.CarteiraEntity;
import com.mensal.entities.MetaEntity;
import com.mensal.services.CarteiraService;
import com.mensal.services.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/save")
    public ResponseEntity<MetaEntity> save(@RequestBody MetaEntity meta) {
        MetaEntity savedMeta = metaService.save(meta);
        return ResponseEntity.ok(savedMeta);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<MetaEntity> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(metaService.findById(id));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllByCarteira/{id}")
    public ResponseEntity<List<MetaEntity>> findAllByCarteira(@PathVariable Long id){
        try{
            CarteiraEntity carteira = carteiraService.findById(id);
            return ResponseEntity.ok(metaService.findAllByCarteira(carteira));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<MetaEntity> edit(@RequestBody MetaEntity meta) {
        MetaEntity updatedMeta = metaService.edit(meta);
        if (updatedMeta != null) {
            return ResponseEntity.ok(updatedMeta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        metaService.delete(id);
        return ResponseEntity.ok("Deletado com Sucesso");
    }
}
