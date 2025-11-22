package com.example.netpick_back.demo.config;

import java.util.Locale;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.netpick_back.demo.model.Categoria;
import com.example.netpick_back.demo.model.Comuna;
import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.model.Estado;
import com.example.netpick_back.demo.model.MetodoEnvio;
import com.example.netpick_back.demo.model.MetodoPago;
import com.example.netpick_back.demo.model.Producto;
import com.example.netpick_back.demo.model.Region;
import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.model.Venta;
import com.example.netpick_back.demo.model.VentaProductos;
import com.example.netpick_back.demo.repository.CategoriaRepository;
import com.example.netpick_back.demo.repository.ComunaRepository;
import com.example.netpick_back.demo.repository.DireccionesRepository;
import com.example.netpick_back.demo.repository.EstadoRepository;
import com.example.netpick_back.demo.repository.MetodoEnvioRepository;
import com.example.netpick_back.demo.repository.MetodoPagoRepository;
import com.example.netpick_back.demo.repository.ProductoRepository;
import com.example.netpick_back.demo.repository.RegionRepository;
import com.example.netpick_back.demo.repository.RolRepository;
import com.example.netpick_back.demo.repository.UsuarioRepository;
import com.example.netpick_back.demo.repository.VentaProductosRepository;
import com.example.netpick_back.demo.repository.VentaRepository;

import net.datafaker.Faker;

@Configuration
public class DataSeeder {

    @Bean
    public ApplicationRunner seedData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            RegionRepository regionRepository,
            ComunaRepository comunaRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            MetodoPagoRepository metodoPagoRepository,
            MetodoEnvioRepository metodoEnvioRepository,
            EstadoRepository estadoRepository,
            DireccionesRepository direccionesRepository,
            VentaRepository ventaRepository,
            VentaProductosRepository ventaProductosRepository) {

        return args -> {

            Faker faker = new Faker(new Locale("es"));

            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol("ADMIN"));
                rolRepository.save(new Rol("CLIENTE"));
            }

            Rol rolAdmin = rolRepository.findByNombre("ADMIN")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
            Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario("Admin", "admin@correo.com", "admin123", "999999999", rolAdmin));
                usuarioRepository.save(new Usuario("Cliente", "cliente@correo.com", "cliente123", "888888888", rolCliente));
            }

            Usuario usuarioCliente = usuarioRepository.findByNombre("Cliente")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Usuario Cliente no encontrado"));

            if (regionRepository.count() == 0) {
                regionRepository.save(new Region("Región Metropolitana"));
                regionRepository.save(new Region("Valparaíso"));
                regionRepository.save(new Region("Biobío"));
            }

            Region regionRM = regionRepository.findByNombre("Región Metropolitana")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Región Metropolitana no encontrada"));
            Region regionV = regionRepository.findByNombre("Valparaíso")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Valparaíso no encontrada"));

            if (comunaRepository.count() == 0) {
                comunaRepository.save(new Comuna("Santiago", regionRM));
                comunaRepository.save(new Comuna("Providencia", regionRM));
                comunaRepository.save(new Comuna("Viña del Mar", regionV));
            }

            Comuna comunaSantiago = comunaRepository.findByNombre("Santiago")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Comuna Santiago no encontrada"));

            if (categoriaRepository.count() == 0) {
                for (int i = 0; i < 5; i++) {
                    categoriaRepository.save(new Categoria(faker.commerce().department(), faker.commerce().material()));
                }
            }

            var categorias = categoriaRepository.findAll();

            if (productoRepository.count() == 0) {
                for (int i = 0; i < 20; i++) {
                    Producto p = new Producto();
                    p.setNombre(faker.commerce().productName());
                    p.setDescripcion(faker.lorem().sentence());
                    p.setPrecio(faker.number().numberBetween(1000, 50000));
                    p.setStock(faker.number().numberBetween(5, 50));
                    p.setLinkImagen("https://picsum.photos/300?random=" + i);
                    p.setCategoria(categorias.get(faker.random().nextInt(categorias.size())));
                    productoRepository.save(p);
                }
            }

            if (metodoPagoRepository.count() == 0) {
                metodoPagoRepository.save(new MetodoPago("Tarjeta de crédito"));
                metodoPagoRepository.save(new MetodoPago("Tarjeta de débito"));
                metodoPagoRepository.save(new MetodoPago("Transferencia bancaria"));
            }

            if (metodoEnvioRepository.count() == 0) {
                metodoEnvioRepository.save(new MetodoEnvio("Envío a domicilio"));
                metodoEnvioRepository.save(new MetodoEnvio("Retiro en tienda"));
            }

            if (estadoRepository.count() == 0) {
                estadoRepository.save(new Estado("Pendiente"));
                estadoRepository.save(new Estado("Confirmado"));
                estadoRepository.save(new Estado("Enviado"));
            }

            Estado estadoPendiente = estadoRepository.findByNombre("Pendiente")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Estado Pendiente no encontrado"));

            MetodoPago pagoTarjeta = metodoPagoRepository.findByNombre("Tarjeta de crédito")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
            MetodoEnvio envioDomicilio = metodoEnvioRepository.findByNombre("Envío a domicilio")
                    .stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Método de envío no encontrado"));

            if (direccionesRepository.count() == 0) {
                Direcciones d = new Direcciones("Av. Siempre Viva 742", "1230000", "Chile", usuarioCliente, comunaSantiago);
                direccionesRepository.save(d);
            }

            if (ventaRepository.count() == 0) {
                Venta v = new Venta();
                v.setUsuario(usuarioCliente);
                v.setMetodoPago(pagoTarjeta);
                v.setMetodoEnvio(envioDomicilio);
                v.setEstado(estadoPendiente);
                ventaRepository.save(v);

                var productos = productoRepository.findAll();

                for (int i = 0; i < 3; i++) {
                    VentaProductos vp = new VentaProductos();
                    vp.setVenta(v);
                    vp.setProducto(productos.get(faker.random().nextInt(productos.size())));
                    ventaProductosRepository.save(vp);
                }
            }
        };
    }
}
