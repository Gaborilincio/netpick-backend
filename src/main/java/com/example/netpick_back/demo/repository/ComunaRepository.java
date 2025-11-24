package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.netpick_back.demo.model.Comuna;
import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.model.Usuario;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    Optional<Comuna> findByIdComuna (Integer idComuna);
    List<Comuna> findByNombre (String nombre);
    List<Direcciones> findByUsuario(Usuario usuario);

}
