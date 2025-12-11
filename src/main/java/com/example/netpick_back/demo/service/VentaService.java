package com.example.netpick_back.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.DTO.VentaDetalleDTO;
import com.example.netpick_back.DTO.VentaHistorialDTO;
import com.example.netpick_back.DTO.VentaRequestDTO;
import com.example.netpick_back.demo.model.Estado;
import com.example.netpick_back.demo.model.MetodoEnvio;
import com.example.netpick_back.demo.model.MetodoPago;
import com.example.netpick_back.demo.model.Producto;
import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.model.Venta;
import com.example.netpick_back.demo.model.VentaProductos;
import com.example.netpick_back.demo.repository.EstadoRepository;
import com.example.netpick_back.demo.repository.MetodoEnvioRepository;
import com.example.netpick_back.demo.repository.MetodoPagoRepository;
import com.example.netpick_back.demo.repository.ProductoRepository;
import com.example.netpick_back.demo.repository.UsuarioRepository;
import com.example.netpick_back.demo.repository.VentaProductosRepository;
import com.example.netpick_back.demo.repository.VentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaProductosRepository ventaProductosRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;
    @Autowired
    private MetodoEnvioRepository metodoEnvioRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public Venta findById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta partialUpdate(Venta venta) {
        Venta existing = ventaRepository.findById(venta.getIdVenta()).orElse(null);
        if (existing != null) {
            if (venta.getMetodoPago() != null) {
                existing.setMetodoPago(venta.getMetodoPago());
            }
            if (venta.getMetodoEnvio() != null) {
                existing.setMetodoEnvio(venta.getMetodoEnvio());
            }
            if (venta.getEstado() != null) {
                existing.setEstado(venta.getEstado());
            }
            if (venta.getUsuario() != null) {
                existing.setUsuario(venta.getUsuario());
            }
            return ventaRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        ventaRepository.deleteById(id);
    }

    @Transactional
    public Venta realizarCompra(VentaRequestDTO request) {

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado."));
        MetodoPago mp = metodoPagoRepository.findById(request.getIdMetodoPago())
                .orElseThrow(() -> new RuntimeException("Error: Método de Pago no encontrado."));
        MetodoEnvio me = metodoEnvioRepository.findById(request.getIdMetodoEnvio())
                .orElseThrow(() -> new RuntimeException("Error: Método de Envío no encontrado."));
        Estado estado = estadoRepository.findById(request.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Error: Estado de Venta no encontrado."));

        Integer totalVenta = 0;
        for (var item : request.getProductos()) {
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Error: Producto con ID " + item.getIdProducto() + " no encontrado."));

            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Error: Stock insuficiente para el producto: " + producto.getNombre() + ". Disponible: " + producto.getStock());
            }

            Integer precioUnitario = producto.getPrecio();
            Integer subtotalItem = precioUnitario * item.getCantidad();
            totalVenta += subtotalItem;

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuario);
        nuevaVenta.setMetodoPago(mp);
        nuevaVenta.setMetodoEnvio(me);
        nuevaVenta.setEstado(estado);
        nuevaVenta.setTotalVenta(totalVenta);
        nuevaVenta.setFechaVenta(LocalDateTime.now());

        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        for (var item : request.getProductos()) {
            Producto producto = productoRepository.findById(item.getIdProducto()).get();

            VentaProductos detalle = new VentaProductos();
            detalle.setVenta(ventaGuardada);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            ventaProductosRepository.save(detalle);
        }

        return ventaGuardada;
    }

public List<VentaHistorialDTO> obtenerHistorial(Integer idUsuario) {
    List<Venta> ventas = ventaRepository.findHistorialByUsuarioId(idUsuario); 
    return ventas.stream()
            .map(venta -> {
                VentaHistorialDTO dto = new VentaHistorialDTO();
                dto.setIdVenta(venta.getIdVenta());
                dto.setFechaVenta(venta.getFechaVenta());
                dto.setTotalVenta(venta.getTotalVenta());
                dto.setEstadoNombre(venta.getEstado() != null ? venta.getEstado().getNombre() : "N/A");
                if (venta.getDetallesVenta() != null) {
                    List<VentaDetalleDTO> detalles = venta.getDetallesVenta().stream()
                        .map(detalle -> {
                            VentaDetalleDTO detalleDto = new VentaDetalleDTO();
                            detalleDto.setNombreProducto(detalle.getProducto().getNombre()); 
                            detalleDto.setPrecioUnitario(detalle.getPrecioUnitario()); 
                            detalleDto.setCantidad(detalle.getCantidad());
                            return detalleDto;
                        })
                        .collect(Collectors.toList());
                    dto.setProductos(detalles);
                }
                
                return dto;
            })
            .collect(Collectors.toList());
}
}
