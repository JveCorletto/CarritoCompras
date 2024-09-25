package com.ufg.parcial_2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/CarritoCompras")
    public String CarritoCompras() {
        return "CarritoCompras";
    }

    @GetMapping("/IngresoProductos")
    public String IngresoProductos() {
        return "IngresoProductos";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}