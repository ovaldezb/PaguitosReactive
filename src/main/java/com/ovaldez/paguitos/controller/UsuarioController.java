package com.ovaldez.paguitos.controller;

import com.ovaldez.paguitos.dto.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @GetMapping
    public ResponseEntity<Usuario> getUserName(){
        Usuario userName = new Usuario(System.getProperty("user.name"));
        return ResponseEntity.ok(userName);
    }
}
