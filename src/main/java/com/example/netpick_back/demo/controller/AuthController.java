package com.example.netpick_back.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.RolRepository;
import com.example.netpick_back.demo.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {

        String correo = request.get("correo");
        String clave = request.get("clave");
        String nombre = request.get("nombre");
        String telefono = request.get("telefono"); 

        if (correo == null || clave == null || nombre == null || telefono == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios: nombre, correo, clave, telefono.");
        }

        if (usuarioService.findByCorreo(correo).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }

        Optional<Rol> defaultRol = rolRepository.findByIdRol(2);
        if (defaultRol.isEmpty()) {
            return ResponseEntity.internalServerError().body("Error: No existe el rol con id=2.");
        }

        Usuario nuevoUsuario = new Usuario(
            nombre,
            correo,
            passwordEncoder.encode(clave),
            telefono,
            defaultRol.get()
        );

        usuarioService.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String correo = request.get("correo");
        String clave = request.get("clave");

        if (correo == null || clave == null) {
            return ResponseEntity.badRequest().body("Debe ingresar correo y clave.");
        }

        Usuario usuario = usuarioService.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        return ResponseEntity.ok(Map.of(
            "message", "Login exitoso",
            "userId", usuario.getIdUsuario(),
            "nombre", usuario.getNombre(),
            "rol", usuario.getRol().getNombre()
        ));
    }
}
