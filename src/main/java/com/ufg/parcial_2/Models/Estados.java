package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "Estados")
public class Estados {

    @Id
    @Column(name = "IdEstado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdEstado;

    @Column(nullable = false, name = "Estado")
    private String Estado;
}