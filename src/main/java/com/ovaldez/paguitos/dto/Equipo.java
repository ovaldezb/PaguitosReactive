package com.ovaldez.paguitos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    private String marca;
    private String modelo;
    private String noSerie;
    private double costo;
}
