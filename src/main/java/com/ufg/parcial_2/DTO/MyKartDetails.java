package com.ufg.parcial_2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyKartDetails {
    private Long idDetalleCompra;
    private Integer cantidad;
    private String producto;
    private String imagen;
    private Double precioUnitario;
    private Double SubTotal;
}