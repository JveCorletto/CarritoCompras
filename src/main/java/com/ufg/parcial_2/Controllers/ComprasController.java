package com.ufg.parcial_2.Controllers;
import com.ufg.parcial_2.Services.ComprasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("API/Compras")
public class ComprasController {
    @Autowired
    private ComprasService comprasService;
}