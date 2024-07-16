package com.ovaldez.paguitos.repository;

import com.ovaldez.paguitos.dto.Credito;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditoRepository extends ReactiveMongoRepository<Credito, String> {
}
