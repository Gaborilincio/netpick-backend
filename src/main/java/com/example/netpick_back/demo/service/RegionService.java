package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Region;
import com.example.netpick_back.demo.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region findById(Integer id) {
        return regionRepository.findById(id).orElse(null);
    }

    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public Region partialUpdate(Region region) {
        Region existing = regionRepository.findById(region.getIdRegion()).orElse(null);
        if (existing != null) {
            if (region.getNombre() != null) {
                existing.setNombre(region.getNombre());
            }
            return regionRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        regionRepository.deleteById(id);
    }
}