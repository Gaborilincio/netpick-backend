package com.example.netpick_back.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.stream.Collectors; 
import java.util.List;
import com.example.netpick_back.DTO.VentaHistorialDTO;
import com.example.netpick_back.DTO.VentaRequestDTO;
import com.example.netpick_back.demo.model.Venta;
import com.example.netpick_back.demo.service.VentaService;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/checkout")
    public ResponseEntity<?> crearVentaTransaccional(@RequestBody VentaRequestDTO request) {
        try {
            Venta ventaRealizada = ventaService.realizarCompra(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(ventaRealizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al procesar la compra.");
        }
    }

    @GetMapping("/historial/{idUsuario}")
    public ResponseEntity<List<VentaHistorialDTO>> getHistorialCompras(@PathVariable Integer idUsuario) {
        List<VentaHistorialDTO> historial = ventaService.obtenerHistorial(idUsuario);

        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(historial);
    }

    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> list = ventaService.findAll();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Integer id) {
        Venta venta = ventaService.findById(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        venta.setIdVenta(id);
        Venta updatedVenta = ventaService.save(venta);
        if (updatedVenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedVenta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Venta> partialUpdateVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        Venta existingVenta = ventaService.findById(id);
        if (existingVenta == null) {
            return ResponseEntity.notFound().build();
        }
        venta.setIdVenta(id);
        return ResponseEntity.ok(ventaService.partialUpdate(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Integer id) {
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
