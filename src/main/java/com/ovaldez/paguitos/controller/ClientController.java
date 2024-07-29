package com.ovaldez.paguitos.controller;

import com.ovaldez.paguitos.dto.Cliente;
import com.ovaldez.paguitos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:4200"})
public class ClientController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Flux<Cliente>> getAllClientes(){
        return ResponseEntity.ok(clienteService.getAllClientes());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Flux<Cliente>> getClientesByName(@PathVariable String nombre){
        return ResponseEntity.ok(this.clienteService.getClientesByName(nombre));
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
    public ResponseEntity<Mono<Cliente>> deleteCliente(@PathVariable final String idCliente){
        return ResponseEntity.ok(clienteService.deleteCliente(idCliente));
    }

}
