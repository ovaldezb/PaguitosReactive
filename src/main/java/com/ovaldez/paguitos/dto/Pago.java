package com.ovaldez.paguitos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    private Date fechaPago;
    private double montoPago;
}
