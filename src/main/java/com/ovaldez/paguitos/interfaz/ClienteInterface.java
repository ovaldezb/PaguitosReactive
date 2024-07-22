package com.ovaldez.paguitos.interfaz;

import com.ovaldez.paguitos.dto.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteInterface {

    Flux<Cliente> getAllClientes();
    Mono<Cliente> getClienteById(String idCliente);
    Mono<Cliente> updateCliente(String idCliente, Cliente cliente);
    Mono<Void> deleteCliente(String idCliente);
    Flux<Cliente> getClientesByName(String nombre);
}
