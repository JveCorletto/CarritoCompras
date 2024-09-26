package com.ufg.parcial_2.Repositories;

import java.util.List;
import java.util.Optional;
import com.ufg.parcial_2.Models.DetallesCompras;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetallesComprasRepository extends JpaRepository<DetallesCompras, Long> {
    @Query("SELECT SUM(dc.SubTotal) FROM DetallesCompras dc WHERE dc.Compra.idCompra = :idCompra")
    Optional<Double> sumByCompraId(Long idCompra);

    @Query("SELECT SUM(dc.Cantidad) FROM DetallesCompras dc WHERE dc.Compra.idCompra = :idCompra")
    Integer sumCantidadByCompra(@Param("idCompra") Long idCompra);

    @Query("SELECT DC FROM DetallesCompras DC WHERE DC.Compra.idCompra = :idCompra AND DC.Producto.IdProducto = :idProducto")
    Optional<DetallesCompras> findByCompraAndProducto(@Param("idCompra") Long idCompra, @Param("idProducto") Long idProducto);

    @Query("SELECT DC FROM DetallesCompras DC WHERE DC.Compra.idCompra = :idCompra")
    List<DetallesCompras> findByIdCompra(@Param("idCompra") Long idCompra);
}