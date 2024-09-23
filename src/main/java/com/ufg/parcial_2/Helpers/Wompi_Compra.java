package com.ufg.parcial_2.Helpers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wompi_Compra {
    private TarjetaCreditoDebido tarjetaCreditoDebido;
    private double monto;
    private Configuracion configuracion;
    private String urlRedirect;
    private String nombre;
    private String apellido;
    private String email;
    private String ciudad;
    private String direccion;
    private String idPais;
    private String idRegion;
    private String codigoPostal;
    private String telefono;
    private DatosAdicionales datosAdicionales;

    @Getter
    @Setter
    public static class TarjetaCreditoDebido {
        private String numeroTarjeta;
        private String cvv;
        private int mesVencimiento;
        private int anioVencimiento;
    }

    @Getter
    @Setter
    public static class Configuracion {
        private String emailsNotificacion;
        private String urlWebhook;
        private String telefonosNotificacion;
        private boolean notificarTransaccionCliente;
    }

    @Getter
    @Setter
    public static class DatosAdicionales {
        private String additionalProp1;
        private String additionalProp2;
        private String additionalProp3;
    }
}