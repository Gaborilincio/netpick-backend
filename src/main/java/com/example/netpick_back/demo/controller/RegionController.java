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


import com.example.netpick_back.demo.model.Region;
import com.example.netpick_back.demo.service.RegionService;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> carreras = regionService.findAll();
        if (carreras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Integer id) {
        Region region = regionService.findById(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }

    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody Region region) {
        Region createdRegion = regionService.save(region);
        return ResponseEntity.status(201).body(createdRegion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> updateRegion(@PathVariable Integer id, @RequestBody Region region) {
        region.setIdRegion(id);
        Region updatedRegion = regionService.save(region);
        if (updatedRegion == null) {
            return ResponseEntity.notFound().build();  
        }
        return ResponseEntity.ok(updatedRegion);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Region> partialUpdateRegion(@PathVariable Integer id, @RequestBody Region region) {
        Region existingRegion = regionService.findById(id);
        if (existingRegion == null) {
            return ResponseEntity.notFound().build();  
        }
        return ResponseEntity.ok(regionService.partialUpdate(region));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        regionService.deleteById(id);
        return ResponseEntity.noContent().build();  
    }
}