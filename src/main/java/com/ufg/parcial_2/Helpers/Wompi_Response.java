package com.ufg.parcial_2.Helpers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wompi_Response {
    private String idTransaccion;
    private boolean esReal;
    private boolean esAprobada;
    private String codigoAutorizacion;
    private String mensaje;
    private String formaPago;
    private double monto;
}