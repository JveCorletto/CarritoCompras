package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "EstadosCompras")
public class EstadosCompras {
    @Id
    @Column(name = "IdEstadoCompra")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdEstadoCompra;

    @Column(nullable = false, name = "EstadoCompra")
    private String EstadoCompra;
}