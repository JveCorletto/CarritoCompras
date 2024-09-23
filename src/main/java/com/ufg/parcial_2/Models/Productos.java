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
@Table(name = "Productos")
public class Productos {
    @Id
    @Column(name = "IdProducto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdProducto;

    @ManyToOne
    @JoinColumn(name = "IdCategoria", nullable = false)
    private Categorias Categoria;

    @Column(nullable = false, name = "Producto")
    private String Producto;

    @Column(nullable = false, name = "Descripcion")
    private String Descripcion;

    @Column(name = "Imagen")
    private String Imagen;

    @Column(nullable = false, name = "Precio")
    private Double Precio;

    @Column(nullable = false, name = "Stock")
    private int Stock;

    // Auditoría
    @Column(nullable = false, name = "UsuarioCreacion")
    private String UsuarioCreacion;

    @Column(nullable = false, name = "FechaCreacion")
    private LocalDate FechaCreacion;

    @Column(name = "UsuarioModificacion")
    private String UsuarioModificacion;

    @Column(name = "FechaModificacion")
    private LocalDate FechaModificacion;
}