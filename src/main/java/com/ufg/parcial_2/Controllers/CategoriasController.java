package com.ufg.parcial_2.Controllers;

import com.ufg.parcial_2.DTO.APIResponses;
import com.ufg.parcial_2.Models.Categorias;
import com.ufg.parcial_2.Services.CategoriasService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("API/Categorias")
public class CategoriasController {
    @Autowired
    private CategoriasService categoriasService;

    @GetMapping
    public ResponseEntity<APIResponses> getAll() {
        try {
            List<Categorias> categorias = categoriasService.getAllCategorias();
            return ResponseEntity.ok(new APIResponses(1, null, categorias));
        }
        catch(Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al obtener el listado de categorias: " + e.getMessage(), null));
        }
    }
}