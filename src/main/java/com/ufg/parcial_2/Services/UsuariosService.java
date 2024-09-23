package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Usuarios;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.ufg.parcial_2.Helpers.PasswordUtils;
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
        return usuarioRepository.getReferenceById(id);
    }

    public Usuarios saveUsuario(Usuarios usuario) {
        String hashedPassword = PasswordUtils.hashPassword(usuario.getContrasenia());
        usuario.setContrasenia(hashedPassword);
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuarios login(String usuario, String contraseniaIngresada) {
        Optional<Usuarios> usuarioEncontrado = usuarioRepository.findByUsuario(usuario);

        if (usuarioEncontrado.isPresent()) {
            Usuarios usuarioGuardado = usuarioEncontrado.get();
            String contraseniaAlmacenada = usuarioGuardado.getContrasenia();

            if (PasswordUtils.hashPassword(contraseniaIngresada).equals(contraseniaAlmacenada)) {
                return usuarioGuardado;
            }
        }

        return null;
    }

    public Optional<Usuarios> findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }
}