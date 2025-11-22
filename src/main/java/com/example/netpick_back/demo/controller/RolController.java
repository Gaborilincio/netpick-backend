package com.example.netpick_back.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.service.RolService;

@RestController
@RequestMapping("/api/rol")
public class RolController {

    @Autowired
    private RolService regionService;

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRols() {
        List<Rol> carreras = regionService.findAll();
        if (carreras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Integer id) {
        Rol region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }

    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol region) {
        Rol createdRol = regionService.save(region);
        return ResponseEntity.status(201).body(createdRol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Integer id, @RequestBody Rol region) {
        region.setIdRol(id);
        Rol updatedRol = regionService.save(region);
        if (updatedRol == null) {
            return ResponseEntity.notFound().build();  
        }
        return ResponseEntity.ok(updatedRol);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rol> partialUpdateRol(@PathVariable Integer id, @RequestBody Rol region) {
        Rol existingRol = regionService.findById(id);
        if (existingRol == null) {
            return ResponseEntity.notFound().build();  
        }
        return ResponseEntity.ok(regionService.partialUpdate(region));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        regionService.deleteById(id);
        return ResponseEntity.noContent().build();  
    }
}