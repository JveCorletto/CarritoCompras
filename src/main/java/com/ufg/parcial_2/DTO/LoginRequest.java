package com.ufg.parcial_2.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String usuario;
    private String contrasenia;

}