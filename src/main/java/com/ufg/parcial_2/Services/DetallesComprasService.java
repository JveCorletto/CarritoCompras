package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Repositories.DetallesComprasRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DetallesComprasService {
    @Autowired
    private DetallesComprasRepository detallesComprasRepository;


}
