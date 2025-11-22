package com.example.netpick_back.demo.config;

import java.util.Locale;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.netpick_back.demo.model.Categoria;
import com.example.netpick_back.demo.model.Producto;
import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.CategoriaRepository;
import com.example.netpick_back.demo.repository.ProductoRepository;
import com.example.netpick_back.demo.repository.RolRepository;
import com.example.netpick_back.demo.repository.UsuarioRepository;

import net.datafaker.Faker;

@Configuration
public class DataSeeder {

    @Bean
    public ApplicationRunner seedData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {

        return args -> {

            if (rolRepository.count() == 0) {
                Rol admin = new Rol();
                admin.setNombre("ADMIN");
                rolRepository.save(admin);

                Rol cliente = new Rol();
                cliente.setNombre("CLIENTE");
                rolRepository.save(cliente);
            }

            if (usuarioRepository.count() == 0) {
                Rol adminRol = rolRepository.findByNombre("ADMIN").get(0);
                Rol clienteRol = rolRepository.findByNombre("CLIENTE").get(0);

                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setCorreo("admin@correo.com");
                admin.setClave("admin123");
                admin.setTelefono("999999999");
                admin.setRol(adminRol);
                usuarioRepository.save(admin);

                Usuario cliente = new Usuario();
                cliente.setNombre("Cliente");
                cliente.setCorreo("cliente@correo.com");
                cliente.setClave("cliente123");
                cliente.setTelefono("888888888");
                cliente.setRol(clienteRol);
                usuarioRepository.save(cliente);
            }

            Faker faker = new Faker(new Locale("es"));

            if (categoriaRepository.count() == 0) {
                for (int i = 0; i < 5; i++) {
                    Categoria c = new Categoria();
                    c.setNombre(faker.commerce().department());
                    categoriaRepository.save(c);
                }
            }

            if (productoRepository.count() == 0) {
                var categorias = categoriaRepository.findAll();
                for (int i = 0; i < 20; i++) {
                    Producto p = new Producto();
                    p.setNombre(faker.commerce().productName());
                    p.setPrecio(faker.number().numberBetween(1000, 50000));
                    p.setCategoria(categorias.get(faker.random().nextInt(categorias.size())));
                    productoRepository.save(p);
                }
            }
        };
    }
}
