package com.example.netpick_back.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.RolRepository;
import com.example.netpick_back.demo.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
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
        try {
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

            String tokenSimulado = "TOKEN_DE_PRUEBA_123456789";

            return ResponseEntity.ok(Map.of(
                "message", "Login exitoso",
                "userId", usuario.getIdUsuario(),
                "nombre", usuario.getNombre(),
                "correo", usuario.getCorreo(),     
                "telefono", usuario.getTelefono(), 
                "rol", usuario.getRol().getNombre(),
                "token", tokenSimulado
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("ERROR DEL SERVIDOR: " + e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> request) {
        try {
            if (request.get("userId") == null) {
                return ResponseEntity.badRequest().body("ID de usuario requerido.");
            }

            Integer idUsuario = Integer.parseInt(request.get("userId").toString());
            Optional<Usuario> usuarioOpt = Optional.ofNullable(usuarioService.findById(idUsuario));
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = usuarioOpt.get();
            if (request.get("nombre") != null) {
                usuario.setNombre((String) request.get("nombre"));
            }
            if (request.get("telefono") != null) {
                usuario.setTelefono((String) request.get("telefono"));
            }
            if (request.get("correo") != null) {
                usuario.setCorreo((String) request.get("correo"));
            }
            if (request.get("clave") != null && !request.get("clave").toString().isEmpty()) {
                 usuario.setClave(passwordEncoder.encode((String) request.get("clave")));
            }
            Usuario usuarioActualizado = usuarioService.save(usuario);
            String tokenSimulado = "TOKEN_DE_PRUEBA_123456789"; 

            return ResponseEntity.ok(Map.of(
                "message", "Perfil actualizado con éxito",
                "userId", usuarioActualizado.getIdUsuario(),
                "nombre", usuarioActualizado.getNombre(),
                "correo", usuarioActualizado.getCorreo(),
                "telefono", usuarioActualizado.getTelefono(),
                "rol", usuarioActualizado.getRol().getNombre(),
                "token", tokenSimulado
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al actualizar perfil: " + e.getMessage());
        }
    }
}