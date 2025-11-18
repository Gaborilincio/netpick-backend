package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Region;
import com.example.netpick_back.demo.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region findById(Integer id) {
        Region region = regionRepository.findByIdRegion(id).orElse(null);
        return region;
    }

    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public Region partialUpdate(Region region) {
        Region existingRegion = regionRepository.findByIdRegion(region.getIdRegion()).orElse(null);
        if (existingRegion == null) {
            return null;
        }
        if (region.getNombre() != null) {
            existingRegion.setNombre(region.getNombre());
        }
        return regionRepository.save(existingRegion);
    }

    public void deleteById(Integer id) {
        regionRepository.deleteById(id);
    }
}