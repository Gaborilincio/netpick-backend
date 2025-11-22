package com.example.netpick_back.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.netpick_back.demo.model.Comuna;
import com.example.netpick_back.demo.service.ComunaService;

@RestController
@RequestMapping("/api/comuna")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> getAllComunas() {
        List<Comuna> list = comunaService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> getComunaById(@PathVariable Integer id) {
        Comuna comuna = comunaService.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comuna);
    }

    @PostMapping
    public ResponseEntity<Comuna> createComuna(@RequestBody Comuna comuna) {
        return ResponseEntity.status(201).body(comunaService.save(comuna));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> updateComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        comuna.setIdComuna(id);
        Comuna updatedComuna = comunaService.save(comuna);
        if (updatedComuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedComuna);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comuna> partialUpdateComuna(@PathVariable Integer id, @RequestBody Comuna comuna) {
        Comuna existingComuna = comunaService.findById(id);
        if (existingComuna == null) {
            return ResponseEntity.notFound().build();
        }
        comuna.setIdComuna(id);
        return ResponseEntity.ok(comunaService.partialUpdate(comuna));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComuna(@PathVariable Integer id) {
        comunaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}