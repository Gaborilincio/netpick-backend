package com.example.netpick_back.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Categoria; 

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNombre(String nombre);
    Categoria findById(long id);
    void deleteById(long id);
}
