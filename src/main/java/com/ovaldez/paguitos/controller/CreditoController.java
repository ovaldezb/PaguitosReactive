package com.ovaldez.paguitos.controller;

import com.ovaldez.paguitos.dto.Credito;
import com.ovaldez.paguitos.dto.Pago;
import com.ovaldez.paguitos.service.CreditoService;
import com.ovaldez.paguitos.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/credito")
@CrossOrigin(origins = "http://localhost:8080")
public class CreditoController {
    @Autowired
    private CreditoService creditoService;

    @GetMapping
    public ResponseEntity<Flux<Credito>> getCreditosActivos(){
        return ResponseEntity.ok(creditoService.getCreditoByStatus(false) );
    }

    @GetMapping("/todos")
    public ResponseEntity<Flux<Credito>> getAllCreditods(){
        return ResponseEntity.ok(creditoService.getAllCreditos());
    }
    @PostMapping
    public ResponseEntity<Mono<Credito>> addCredito(@RequestBody Credito credito){
        return ResponseEntity.ok(creditoService.addCredito(credito));
    }

    @PutMapping("/{idCredito}/{flag}/{adeudo}")
    public ResponseEntity<Mono<Credito>> addPago(@PathVariable final String idCredito, @PathVariable final String flag, @PathVariable final String adeudo, @RequestBody Pago pago){
        return  ResponseEntity.ok(creditoService.addPago(idCredito,pago, Boolean.valueOf(flag), adeudo));
    }

    @DeleteMapping("/{idCredito}")
    public ResponseEntity<Mono<Void>> removeCredito(@PathVariable final String idCredito){
        return  ResponseEntity.ok(creditoService.removeCredito(idCredito));
    }
}
