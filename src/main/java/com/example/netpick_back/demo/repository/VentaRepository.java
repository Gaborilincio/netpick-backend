package com.example.netpick_back.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.netpick_back.demo.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    Optional<Venta> findByIdVenta (Integer idVenta);

    @Query("SELECT v FROM Venta v " +
           "JOIN FETCH v.estado e " +           
           "JOIN FETCH v.metodoPago mp " +      
           "JOIN FETCH v.metodoEnvio me " +    
           "WHERE v.usuario.idUsuario = :idUsuario")
    List<Venta> findHistorialByUsuarioId(@Param("idUsuario") Integer idUsuario);
}
