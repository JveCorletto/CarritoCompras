package com.ufg.parcial_2.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.time.LocalDate;

@Getter
@Setter
public class MyKartDTO {
    private Long idCompra;
    private String estadoCompra;
    private String usuarioCompra;
    private LocalDate fechaCompra;
    List<MyKartDetails> KartDetails;
}