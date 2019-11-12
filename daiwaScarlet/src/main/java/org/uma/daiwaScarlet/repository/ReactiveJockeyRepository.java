package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Jockey;

@Repository
public interface ReactiveJockeyRepository extends ReactiveMongoRepository<Jockey, String> {

}
