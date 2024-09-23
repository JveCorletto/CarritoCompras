package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "DetallesCompras")
public class DetallesCompras {
    @Id
    @Column(name = "IdDetalleCompra")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdDetalleCompra;

    @ManyToOne
    @JoinColumn(name = "IdCompra", nullable = false)
    private Compras Compra;

    @ManyToOne
    @JoinColumn(name = "IdProducto", nullable = false)
    private Productos Producto;

    @Column(nullable = false, name = "Cantidad")
    private int Cantidad;

    @Column(nullable = false, name = "PrecioUnitario")
    private Double PrecioUnitario;

    @Column(nullable = false, name = "SubTotal")
    private Double SubTotal;
}