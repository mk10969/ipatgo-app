package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Course;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredCourseRepository implements JvLinkStoredRepository<Course> {

    @Override
    public List<Course> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
