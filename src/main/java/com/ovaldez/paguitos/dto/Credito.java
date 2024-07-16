package com.ovaldez.paguitos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "creditos")
public class Credito {
    @Id
    private String id;
    private Equipo equipo;
    private String idCliente;
    @Transient
    private Cliente cliente;
    private int noPagosTotales;
    private double enganche;
    private List<Pago> pagos;
    private String plazoPago;

}
