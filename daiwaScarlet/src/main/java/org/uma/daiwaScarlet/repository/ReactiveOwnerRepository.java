package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Owner;

@Repository
public interface ReactiveOwnerRepository extends ReactiveMongoRepository<Owner, String> {

}
