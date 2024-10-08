package com.ufg.parcial_2.Repositories;
import com.ufg.parcial_2.Models.Productos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    List<Productos> findByLogicalDeletedFalse();
}