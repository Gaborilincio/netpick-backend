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

import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.service.DireccionesService;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionesController {

    @Autowired
    private DireccionesService direccionesService;

    @GetMapping
    public ResponseEntity<List<Direcciones>> getAllDireccioness() {
        List<Direcciones> list = direccionesService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direcciones> getDireccionesById(@PathVariable Integer id) {
        Direcciones direcciones = direccionesService.findById(id);
        if (direcciones == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(direcciones);
    }

    @PostMapping
    public ResponseEntity<Direcciones> createDirecciones(@RequestBody Direcciones direcciones) {
        return ResponseEntity.status(201).body(direccionesService.save(direcciones));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direcciones> updateDirecciones(@PathVariable Integer id, @RequestBody Direcciones direcciones) {
        direcciones.setIdDireccion(id);
        Direcciones updatedDirecciones = direccionesService.save(direcciones);
        if (updatedDirecciones == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDirecciones);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Direcciones> partialUpdateDirecciones(@PathVariable Integer id, @RequestBody Direcciones direcciones) {
        Direcciones existingDirecciones = direccionesService.findById(id);
        if (existingDirecciones == null) {
            return ResponseEntity.notFound().build();
        }
        direcciones.setIdDireccion(id);
        return ResponseEntity.ok(direccionesService.partialUpdate(direcciones));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirecciones(@PathVariable Integer id) {
        direccionesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}