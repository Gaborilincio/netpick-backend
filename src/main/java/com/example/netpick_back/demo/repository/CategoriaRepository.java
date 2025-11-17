package com.example.netpick_back.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.netpick_back.demo.model.Categoria; 

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByIdCategoria (Integer idCategoria);
    List<Categoria> findByNombre (String categoriaNombre);
}
