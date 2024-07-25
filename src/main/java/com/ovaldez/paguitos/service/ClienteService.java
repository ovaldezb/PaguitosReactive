package com.ovaldez.paguitos.service;

import com.ovaldez.paguitos.dto.Cliente;
import com.ovaldez.paguitos.interfaz.ClienteInterface;
import com.ovaldez.paguitos.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.print.FlavorException;


@Service
public class ClienteService implements ClienteInterface {

    @Autowired
    private ClienteRepository clienteRepository;
    public Flux<Cliente> getAllClientes(){
        return clienteRepository.findAll().filter(cliente -> cliente.getIsActive());
    }

    public Mono<Cliente> getClienteById(String idCliente){
        return clienteRepository.findById(idCliente);
    }
    public Mono<Cliente> saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Mono<Cliente> updateCliente(String idCliente, Cliente cliente){
        return this.clienteRepository.findById(idCliente).map(c -> updateClienteBody(c, cliente)).flatMap(clienteRepository::save);
    }

    public Flux<Cliente> getClientesByName(String nombre){
        return this.clienteRepository.findByNombreAndActive(nombre, true);
    }
    public Mono<Cliente> deleteCliente(String idCliente){
        return clienteRepository.findById(idCliente)
                .map(cliente ->{
                    cliente.setIsActive(false);
                    return cliente;
                }).flatMap( cliente ->{
                            return clienteRepository.save(cliente);
                    }
                );
    }

    private Cliente updateClienteBody(Cliente clienteUpdted, Cliente clienteOriginal){
        //clienteUpdted.setApellidoM(clienteOriginal.getApellidoM());
        //clienteUpdted.setApellidoP(clienteOriginal.getApellidoP());
        clienteUpdted.setNombre(clienteOriginal.getNombre());
        clienteUpdted.setDireccion(clienteOriginal.getDireccion());
        clienteUpdted.setTelefono(clienteOriginal.getTelefono());
        clienteUpdted.setCorreoElectronico(clienteOriginal.getCorreoElectronico());
        return  clienteUpdted;
    }
}
