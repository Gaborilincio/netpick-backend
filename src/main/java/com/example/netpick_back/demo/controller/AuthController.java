package com.example.netpick_back.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netpick_back.demo.model.Comuna;
import com.example.netpick_back.demo.model.Direcciones;
import com.example.netpick_back.demo.model.Rol;
import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.ComunaRepository;
import com.example.netpick_back.demo.repository.DireccionesRepository;
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

    @Autowired
    private DireccionesRepository direccionesRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {

        String correo = request.get("correo");
        String clave = request.get("clave");
        String nombre = request.get("nombre");
        String telefono = request.get("telefono");

        if (correo == null || clave == null || nombre == null || telefono == null) {
            return ResponseEntity.badRequest().body("Todos los campos son obligatorios");
        }

        if (usuarioService.findByCorreo(correo).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        Optional<Rol> defaultRol = rolRepository.findByIdRol(2);
        if (defaultRol.isEmpty()) {
            return ResponseEntity.internalServerError().body("Error: No existe el rol con id=2");
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
                return ResponseEntity.badRequest().body("Debe ingresar correo y clave");
            }

            Usuario usuario = usuarioService.findByCorreo(correo).orElse(null);

            if (usuario == null) {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }

            if (!passwordEncoder.matches(clave, usuario.getClave())) {
                return ResponseEntity.status(401).body("Credenciales inválidas");
            }
            
            String direccionStr = "";
            List<Direcciones> direcciones = direccionesRepository.findByUsuario(usuario);
            if (!direcciones.isEmpty()) {
                direccionStr = direcciones.get(0).getDireccion();
            }

            return ResponseEntity.ok(Map.of(
                "message", "Login exitoso",
                "userId", usuario.getIdUsuario(),
                "nombre", usuario.getNombre(),
                "correo", usuario.getCorreo(),
                "telefono", usuario.getTelefono() != null ? usuario.getTelefono() : "",
                "direccion", direccionStr,
                "rol", usuario.getRol().getNombre()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("ERROR DEL SERVIDOR: " + e.getMessage());
        }
    }

   @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> request, Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("No autorizado: Debe iniciar sesión para editar el perfil");
        }

        String correoUsuario = authentication.getName();
        Usuario usuario = usuarioService.findByCorreo(correoUsuario).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        boolean updated = false;

        String newNombre = request.get("nombre");
        if (newNombre != null && !newNombre.isBlank()) {
            usuario.setNombre(newNombre);
            updated = true;
        }

        String newTelefono = request.get("telefono");
        if (newTelefono != null) {
            usuario.setTelefono(newTelefono.trim()); 
            updated = true;
        }

        if (updated) {
            usuarioService.save(usuario);
        }

        String newDireccionStr = request.get("direccion");
        
        if (newDireccionStr != null) {
            List<Direcciones> direccionesList = direccionesRepository.findByUsuario(usuario);
            Direcciones direccion;
            
            if (!direccionesList.isEmpty()) {
                direccion = direccionesList.get(0);
                if (!direccion.getDireccion().equals(newDireccionStr)) {
                    direccion.setDireccion(newDireccionStr);
                    direccionesRepository.save(direccion);
                    updated = true;
                }
            } else if (!newDireccionStr.isBlank()) {
                Optional<Comuna> defaultComunaOpt = comunaRepository.findById(1); 
                if (defaultComunaOpt.isPresent()) {
                    direccion = new Direcciones(
                        newDireccionStr,
                        "00000",
                        "Chile",
                        usuario,
                        defaultComunaOpt.get()
                    );
                    direccionesRepository.save(direccion);
                    updated = true;
                }
            }
        }
        String finalDireccionStr = "";
        List<Direcciones> dirs = direccionesRepository.findByUsuario(usuario);
        if (!dirs.isEmpty()) {
            finalDireccionStr = dirs.get(0).getDireccion();
        }

        return ResponseEntity.ok(Map.of(
            "message", "Perfil actualizado con éxito",
            "userId", usuario.getIdUsuario(),
            "nombre", usuario.getNombre(),
            "correo", usuario.getCorreo(),
            "telefono", usuario.getTelefono() != null ? usuario.getTelefono() : "",
            "direccion", finalDireccionStr,
            "rol", usuario.getRol().getNombre()
        ));
    }
}