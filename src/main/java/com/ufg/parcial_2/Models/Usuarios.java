package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "Usuarios")
public class Usuarios {
    @Id
    @Column(name = "IdUsuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "IdEstado", nullable = false)
    private Estados estado;

    @ManyToOne
    @JoinColumn(name = "IdRol", nullable = false)
    private Roles rol;

    @Column(nullable = false, name = "Nombre")
    private String nombre;

    @Column(nullable = false, name = "Usuario")
    private String usuario;

    @Column(nullable = false, name = "Contrasenia")
    private String contrasenia;
}