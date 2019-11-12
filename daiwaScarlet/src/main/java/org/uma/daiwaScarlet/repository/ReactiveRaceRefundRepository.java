package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.RaceRefund;

@Repository
public interface ReactiveRaceRefundRepository extends ReactiveMongoRepository<RaceRefund, String> {

}
