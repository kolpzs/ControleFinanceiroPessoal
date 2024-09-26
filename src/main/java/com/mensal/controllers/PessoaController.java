package com.mensal.controllers;

import com.mensal.entities.PessoaEntity;
import com.mensal.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/save")
    public ResponseEntity<PessoaEntity> save(@RequestBody PessoaEntity pessoa) {
        PessoaEntity savedPessoa = pessoaService.save(pessoa);
        return ResponseEntity.ok(savedPessoa);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<PessoaEntity> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(pessoaService.findById(id));
        } catch (Exception e) {
            System.err.println(e.getCause());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PessoaEntity>> findAll(){
        try{
            return ResponseEntity.ok(pessoaService.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<PessoaEntity> edit(@RequestBody PessoaEntity pessoa) {
        PessoaEntity updatedPessoa = pessoaService.edit(pessoa);
        if (updatedPessoa != null) {
            return ResponseEntity.ok(updatedPessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
