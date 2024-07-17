package com.ovaldez.paguitos.repository;

import com.ovaldez.paguitos.dto.Credito;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditoRepository extends ReactiveMongoRepository<Credito, String> {

    Flux<Credito> findByIsPagado(boolean flag);
}
