package com.ovaldez.paguitos.controller;

import com.ovaldez.paguitos.dto.Cliente;
import com.ovaldez.paguitos.repository.ClienteRepository;
import com.ovaldez.paguitos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cliente")
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Flux<Cliente>> getAllClientes(){
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @PostMapping
    public ResponseEntity<Mono<Cliente>> createCliente(@RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.saveCliente(cliente));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Mono<Cliente>> updateCliente(@PathVariable final String idCliente, @RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.updateCliente(idCliente,cliente));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Mono<Void>> deleteCliente(@PathVariable final String idCliente){
        return ResponseEntity.ok(clienteService.deleteCliente(idCliente));
    }

}
