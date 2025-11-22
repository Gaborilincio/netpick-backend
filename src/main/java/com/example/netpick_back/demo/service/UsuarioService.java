package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario partialUpdate(Usuario usuario) {
        Usuario existing = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        if (existing != null) {
            if (usuario.getNombre() != null) {
                existing.setNombre(usuario.getNombre());
            }
            if (usuario.getCorreo() != null) {
                existing.setCorreo(usuario.getCorreo());
            }
            if (usuario.getClave() != null) {
                existing.setClave(usuario.getClave());
            }
            if (usuario.getTelefono() != null) {
                existing.setTelefono(usuario.getTelefono());
            }
            if (usuario.getRol() != null) {
                existing.setRol(usuario.getRol());
            }
            return usuarioRepository.save(existing);
        }
        return null;
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}