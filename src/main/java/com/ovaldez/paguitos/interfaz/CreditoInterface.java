package com.ovaldez.paguitos.interfaz;

import com.ovaldez.paguitos.dto.Credito;
import com.ovaldez.paguitos.dto.Pago;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditoInterface {
    Flux<Credito> getAllCreditos();
    Mono<Credito> addCredito(Credito credito);
    Mono<Credito> addPago(String idCredito, Pago pago, boolean flag);
    Mono<Void> removeCredito(String idCredito);
    Flux<Credito> getCreditoByStatus(boolean flag);
}
