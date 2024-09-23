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
    private Long IdCompra;

    @ManyToOne
    @JoinColumn(name = "IdEstadoCompra", nullable = false)
    private EstadosCompras EstadoCompra;

    @OneToOne
    @JoinColumn(name = "IdComprobante", nullable = false)
    private ComprobantesCompras Comprobante;

    @ManyToOne
    @JoinColumn(name = "IdUsuario", nullable = false)
    private Usuarios IdUsuario;

    @Column(nullable = false, name = "FechaCompra")
    private LocalDate FechaCompra;

    @Column(nullable = false, name = "TotalCompra")
    private Double TotalCompra;
}