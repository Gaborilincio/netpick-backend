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
import com.example.netpick_back.demo.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/auth") 
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ENDPOINT DE REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String password = request.get("password");

        if (usuarioRepository.findByCorreo(correo).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setCorreo(correo);
        newUsuario.setClave(passwordEncoder.encode(password)); 

        usuarioRepository.save(newUsuario);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        String password = request.get("password");

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        if (!passwordEncoder.matches(password, usuario.getClave())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        return ResponseEntity.ok(Map.of("message", "Login exitoso", "usuarioId", usuario.getIdUsuario()));
    }
}