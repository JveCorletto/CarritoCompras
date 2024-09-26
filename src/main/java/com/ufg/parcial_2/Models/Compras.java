package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@Table(name = "Compras")
public class Compras {
    @Id
    @Column(name = "IdCompra")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompra;

    @ManyToOne
    @JoinColumn(name = "IdEstadoCompra", nullable = false)
    private EstadosCompras estadoCompra;

    @ManyToOne
    @JoinColumn(name = "IdUsuario", nullable = false)
    private Usuarios idUsuario;

    @Column(nullable = false, name = "FechaCompra")
    private LocalDate fechaCompra;

    @Column(nullable = true, name = "TotalCompra")
    private Double totalCompra;
}