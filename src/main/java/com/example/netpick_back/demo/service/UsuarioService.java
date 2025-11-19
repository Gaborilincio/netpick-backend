package com.example.netpick_back.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpick_back.demo.model.Usuario;
import com.example.netpick_back.demo.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        Usuario usuario = usuarioRepository.findByIdUsuario(id).orElse(null);
        return usuario;
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario partialUpdate(Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findByIdUsuario(usuario.getIdUsuario()).orElse(null);
        if (existingUsuario == null) {
            return null;
        }
        if (usuario.getNombre() != null) {
            existingUsuario.setNombre(usuario.getNombre());
        }
        if (usuario.getCorreo() != null) {
            existingUsuario.setCorreo(usuario.getCorreo());
        }
        if (usuario.getClave() != null) {
            existingUsuario.setClave(usuario.getClave());
        }
        if (usuario.getTelefono() != null) {
            existingUsuario.setTelefono(usuario.getTelefono());
        }
        if (usuario.getRol() != null) {
            existingUsuario.setRol(usuario.getRol());
        }
        return usuarioRepository.save(existingUsuario);
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}