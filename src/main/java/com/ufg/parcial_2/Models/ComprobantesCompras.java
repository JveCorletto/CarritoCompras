package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "ComprobantesCompras")
public class ComprobantesCompras {
    @Id
    @Column(name = "IdComprobante")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdComprobante;

    @ManyToOne
    @JoinColumn(name = "IdCompra", nullable = false)
    private Compras Compra;

    @Column(nullable = false, name = "Tarjeta")
    private String Tarjeta;

    @Column(nullable = false, name = "TokenComprobante")
    private String TokenComprobante;

    @Column(nullable = false, name = "FechaTransaccion")
    private Date FechaTransaccion;
}