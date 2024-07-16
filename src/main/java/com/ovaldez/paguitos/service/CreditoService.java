package com.ovaldez.paguitos.service;

import com.ovaldez.paguitos.dto.Credito;
import com.ovaldez.paguitos.dto.Pago;
import com.ovaldez.paguitos.interfaz.CreditoInterface;
import com.ovaldez.paguitos.repository.ClienteRepository;
import com.ovaldez.paguitos.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Service
public class CreditoService implements CreditoInterface {

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    ClienteRepository clienteRepository;
    public Flux<Credito> getAllCreditos(){
        return creditoRepository.findAll()
                .flatMap(credito -> {
                    return Mono.just(credito)
                    .zipWith(clienteRepository.findById(credito.getIdCliente()), (u,p)->{
                        u.setCliente(p);
                        return u;
                    });
                });
    }

    public Mono<Credito> addCredito(Credito credito){
        return creditoRepository.insert(credito);
    }

    public Mono<Credito> addPago(String idCredito, Pago pago){
        return creditoRepository
                .findById(idCredito)
                .map(credito -> {
                    List<Pago> listaPagos = credito.getPagos();
                    listaPagos.add(pago);
                    credito.setPagos(listaPagos);
                    return credito;
                })
                .flatMap(c ->{
                    return creditoRepository.save(c);
                });


    }

    public Mono<Void> removeCredito(String idCredito){
        return creditoRepository.deleteById(idCredito);
    }
}
