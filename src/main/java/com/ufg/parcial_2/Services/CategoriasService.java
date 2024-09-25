package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Categorias;
import com.ufg.parcial_2.Repositories.CategoriasRepository;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CategoriasService {
    @Autowired
    private CategoriasRepository categoriasRepository;

    public List<Categorias> getAllCategorias() {
        return categoriasRepository.findAll();
    }
}