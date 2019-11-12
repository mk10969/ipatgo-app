package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.RacingDetails;

@Repository
public interface ReactiveRacingDetailsRepository extends ReactiveMongoRepository<RacingDetails, String> {

}
