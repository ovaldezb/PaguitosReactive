package com.ovaldez.paguitos.repository;

import com.ovaldez.paguitos.dto.Cliente;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String> {

    @Query(value = "{'$and':[{'nombre': { $regex: ?0, $options:'i' }},{'isActive':{$eq:?1}}]}")
    Flux<Cliente> findByNombreAndActive(String nombre,boolean flag);
}
