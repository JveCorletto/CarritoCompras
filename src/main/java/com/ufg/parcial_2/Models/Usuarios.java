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
    private Long IdUsuario;

    @ManyToOne
    @JoinColumn(name = "IdEstado", nullable = false)
    private Estados Estado;

    @ManyToOne
    @JoinColumn(name = "IdRol", nullable = false)
    private Roles Rol;

    @Column(nullable = false, name = "Nombre")
    private String Nombre;

    @Column(nullable = false, name = "Usuario")
    private String usuario;

    @Column(nullable = false, name = "Contrasenia")
    private String Contrasenia;
}