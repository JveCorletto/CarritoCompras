package com.ufg.parcial_2.Repositories;
import com.ufg.parcial_2.Models.Compras;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ComprasRepository extends JpaRepository<Compras, Long> {
    @Query("SELECT c FROM Compras c WHERE c.idUsuario.idUsuario = :idUsuario AND c.estadoCompra.idEstadoCompra = 1 ORDER BY c.idCompra DESC")
    Optional<Compras> getActiveCompra(@Param("idUsuario") Long idUsuario);
}