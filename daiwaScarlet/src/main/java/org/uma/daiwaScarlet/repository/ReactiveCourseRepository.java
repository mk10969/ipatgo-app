package org.uma.daiwaScarlet.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Course;

@Repository
public interface ReactiveCourseRepository extends ReactiveMongoRepository<Course, String> {

}
