package com.ufg.parcial_2.Repositories;

import com.ufg.parcial_2.Models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    Optional<Usuarios> findByUsuario(String usuario);
}