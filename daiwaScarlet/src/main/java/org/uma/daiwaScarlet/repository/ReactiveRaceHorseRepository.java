package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.RaceHorse;

@Repository
public interface ReactiveRaceHorseRepository extends ReactiveMongoRepository<RaceHorse, String> {

}
