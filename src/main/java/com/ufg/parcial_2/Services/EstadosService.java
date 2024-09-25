package com.ufg.parcial_2.Services;

import com.ufg.parcial_2.Models.Estados;
import org.springframework.stereotype.Service;
import com.ufg.parcial_2.Repositories.EstadosRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstadosService {
    @Autowired
    private EstadosRepository estadosRepository;

    public Estados findById(Integer id) {
        return estadosRepository.findById(id).orElse(null);
    }
}
