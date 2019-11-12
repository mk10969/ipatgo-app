package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.BreedingHorse;

@Repository
public interface ReactiveBreedingHorseRepository extends ReactiveMongoRepository<BreedingHorse, String> {

}
