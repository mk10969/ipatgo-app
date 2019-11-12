package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Breeder;

@Repository
public interface ReactiveBreederRepository extends ReactiveMongoRepository<Breeder, String> {

}
