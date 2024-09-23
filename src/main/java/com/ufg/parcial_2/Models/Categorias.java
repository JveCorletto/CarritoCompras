package com.ufg.parcial_2.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "Categorias")
public class Categorias {
    @Id
    @Column(name = "IdCategoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdCategoria;

    @Column(nullable = false, name = "Categoria")
    private String Categoria;
}