package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Offspring;

@Repository
public interface ReactiveOffspringRepository extends ReactiveMongoRepository<Offspring, String> {

}
