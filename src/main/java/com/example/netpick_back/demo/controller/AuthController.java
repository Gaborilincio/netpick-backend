package com.example.netpick_back.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/auth") 
public class AuthController {

    @Autowired
    private UsuarioService usuarioService; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        
        String correo = request.get("correo"); 
        String clave = request.get("clave");
        String nombre = request.get("nombre");

        if (usuarioService.findByCorreo(correo).isPresent()) { 
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(correo); 
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setClave(passwordEncoder.encode(clave));

        usuarioService.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        
        String correo = request.get("correo");
        String clave = request.get("clave");

        Usuario usuario = usuarioService.findByCorreo(correo)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        return ResponseEntity.ok(Map.of("message", "Login exitoso", "userId", usuario.getIdUsuario()));
    }
}