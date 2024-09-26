package com.ufg.parcial_2.Services;

import org.springframework.stereotype.Service;
import com.ufg.parcial_2.Models.EstadosCompras;
import com.ufg.parcial_2.Repositories.EstadosComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EstadosComprasService {
    @Autowired
    private EstadosComprasRepository estadosComprasRepository;

    public EstadosCompras findById(int id) {
        return estadosComprasRepository.findById(id).orElse(null);
    }
}