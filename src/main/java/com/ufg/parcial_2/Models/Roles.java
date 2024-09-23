package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "Roles")
public class Roles {
    @Id
    @Column(name = "IdRol")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdRol;

    @ManyToOne
    @JoinColumn(name = "IdEstado", nullable = false)
    private Estados Estado;

    @Column(nullable = false, name = "Rol")
    private String Rol;
}