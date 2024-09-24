package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Usuarios;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ufg.parcial_2.Security.PasswordUtils;
import com.ufg.parcial_2.Repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuarioRepository;

    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuarios getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuarios saveUsuario(Usuarios usuario) {
        String hashedPassword = PasswordUtils.hashPassword(usuario.getContrasenia());
        usuario.setContrasenia(hashedPassword);
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuarios findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }
}