package com.ufg.parcial_2.Repositories;
import com.ufg.parcial_2.Models.Estados;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadosRepository extends JpaRepository<Estados, Integer> { }