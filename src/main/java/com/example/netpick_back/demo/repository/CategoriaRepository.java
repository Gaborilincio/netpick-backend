package com.example.netpick_back.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.example.netpick_back.demo.model.Categoria; 

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByIdCategoria (Integer idCategoria);
    List<Categoria> findByNombre (String categoriaNombre);
}
