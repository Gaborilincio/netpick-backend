package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByIdRegion (Integer idRegion);
    List<Region> findByNombre (String nombre);
}
