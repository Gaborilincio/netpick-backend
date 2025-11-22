package com.example.netpick_back.demo.controller;

import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Ver qué diablos pasa con el usuario
    @GetMapping("/check/{correo}")
    public String checkUser(@PathVariable String correo) {
        Optional<Usuario> userOpt = usuarioRepository.findByCorreo(correo);
        
        if (userOpt.isEmpty()) {
            return "❌ El usuario NO existe en la base de datos.";
        }

        Usuario u = userOpt.get();
        String info = "✅ Usuario encontrado: " + u.getNombre() + "\n";
        info += "🔑 Hash en BD: " + u.getClave() + "\n";
        info += "📏 Largo del Hash: " + u.getClave().length() + " (Debe ser 60)\n";

        // Probamos la contraseña por defecto del seeder
        boolean coincide = passwordEncoder.matches("admin123", u.getClave());
        info += "🛠 ¿Coincide con 'admin123'?: " + (coincide ? "SÍ ✅" : "NO ❌");

        return info;
    }

    // 2. Botón de Pánico: Resetear contraseña a "1234"
    @PostMapping("/reset/{correo}")
    public String forceReset(@PathVariable String correo) {
        Optional<Usuario> userOpt = usuarioRepository.findByCorreo(correo);
        
        if (userOpt.isPresent()) {
            Usuario u = userOpt.get();
            // Forzamos una encriptación limpia de "1234"
            u.setClave(passwordEncoder.encode("1234")); 
            usuarioRepository.save(u);
            return "✅ Contraseña restablecida a '1234' para " + correo;
        }
        return "❌ Usuario no encontrado";
    }
}