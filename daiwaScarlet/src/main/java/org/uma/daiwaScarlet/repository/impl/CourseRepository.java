package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.CourseModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class CourseRepository implements JvLinkStoredRepository<CourseModel> {

    @Override
    public List<CourseModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
