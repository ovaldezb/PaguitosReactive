package com.ovaldez.paguitos.controller;

import com.ovaldez.paguitos.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:8080")
public class EmailController {

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/{idCredito}")
    public ResponseEntity<Mono<Void>> sendEmailByCreditoId(@PathVariable String idCredito){
        emailService.sendEmailByCreditoId(idCredito);
        return ResponseEntity.ok(Mono.empty());
    }
}
