package com.ufg.parcial_2.Repositories;

import com.ufg.parcial_2.Models.DetallesCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DetallesComprasRepository extends JpaRepository<DetallesCompras, Long> {
    @Query("SELECT SUM(dc.SubTotal) FROM DetallesCompras dc WHERE dc.Compra.IdCompra = :idCompra")
    Optional<Double> sumByCompraId(Long idCompra);
}