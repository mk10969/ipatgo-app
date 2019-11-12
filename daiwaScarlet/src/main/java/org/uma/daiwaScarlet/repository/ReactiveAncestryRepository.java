package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Ancestry;

@Repository
public interface ReactiveAncestryRepository extends ReactiveMongoRepository<Ancestry, String> {

}
