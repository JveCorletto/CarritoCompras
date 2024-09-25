package com.ufg.parcial_2.Repositories;
import com.ufg.parcial_2.Models.Roles;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findById(Integer IdRol);
}
