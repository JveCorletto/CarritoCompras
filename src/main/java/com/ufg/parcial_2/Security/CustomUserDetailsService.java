package com.ufg.parcial_2.Security;

import java.util.Collection;
import java.util.Collections;
import com.ufg.parcial_2.Models.Usuarios;
import org.springframework.stereotype.Service;
import com.ufg.parcial_2.Repositories.UsuariosRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepository.findByUsuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new User(usuario.getUsuario(), usuario.getContrasenia(), getAuthorities(usuario));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuarios usuario) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
    }
}