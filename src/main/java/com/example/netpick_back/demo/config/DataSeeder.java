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
import com.example.netpick_back.demo.repository.*;

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
                rolRepository.save(new Rol(null, "ADMIN"));
                rolRepository.save(new Rol(null, "CLIENTE"));
            }

            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setCorreo("admin@correo.com");
                admin.setClave("admin123");
                admin.setTelefono("999999999");
                admin.setRol(rolRepository.findByNombre("ADMIN").get(0));
                usuarioRepository.save(admin);

                Usuario cliente = new Usuario();
                cliente.setNombre("Cliente");
                cliente.setCorreo("cliente@correo.com");
                cliente.setClave("cliente123");
                cliente.setTelefono("888888888");
                cliente.setRol(rolRepository.findByNombre("CLIENTE").get(0));
                usuarioRepository.save(cliente);
            }

            if (regionRepository.count() == 0) {
                regionRepository.save(new Region(null, "Región Metropolitana"));
                regionRepository.save(new Region(null, "Valparaíso"));
                regionRepository.save(new Region(null, "Biobío"));
            }

            if (comunaRepository.count() == 0) {
                comunaRepository.save(new Comuna(null, "Santiago", regionRepository.findByNombre("Región Metropolitana").get(0)));
                comunaRepository.save(new Comuna(null, "Providencia", regionRepository.findByNombre("Región Metropolitana").get(0)));
                comunaRepository.save(new Comuna(null, "Viña del Mar", regionRepository.findByNombre("Valparaíso").get(0)));
            }

            if (categoriaRepository.count() == 0) {
                for (int i = 0; i < 5; i++) {
                    categoriaRepository.save(new Categoria(null,
                            faker.commerce().department(),
                            faker.commerce().material()));
                }
            }

            if (productoRepository.count() == 0) {
                var categorias = categoriaRepository.findAll();

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
                metodoPagoRepository.save(new MetodoPago(null, "Tarjeta de crédito"));
                metodoPagoRepository.save(new MetodoPago(null, "Tarjeta de débito"));
                metodoPagoRepository.save(new MetodoPago(null, "Transferencia bancaria"));
            }

            if (metodoEnvioRepository.count() == 0) {
                metodoEnvioRepository.save(new MetodoEnvio(null, "Envío a domicilio"));
                metodoEnvioRepository.save(new MetodoEnvio(null, "Retiro en tienda"));
            }

            if (estadoRepository.count() == 0) {
                estadoRepository.save(new Estado(null, "Pendiente"));
                estadoRepository.save(new Estado(null, "Confirmado"));
                estadoRepository.save(new Estado(null, "Enviado"));
            }

            if (direccionesRepository.count() == 0) {
                Direcciones d = new Direcciones();
                d.setDireccion("Av. Siempre Viva 742");
                d.setCodigoPostal("1230000");
                d.setPais("Chile");
                d.setUsuario(usuarioRepository.findByNombre("Cliente").get(0));
                d.setComuna(comunaRepository.findByNombre("Santiago").get(0));
                direccionesRepository.save(d);
            }

            if (ventaRepository.count() == 0) {
                Venta v = new Venta();
                v.setUsuario(usuarioRepository.findByNombre("Cliente").get(0));
                v.setMetodoPago(metodoPagoRepository.findByNombre("Tarjeta de crédito").get(0));
                v.setMetodoEnvio(metodoEnvioRepository.findByNombre("Envío a domicilio").get(0));
                v.setEstado(estadoRepository.findByNombre("Pendiente").get(0));
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
