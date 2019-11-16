package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.Course;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredCourseRepository implements JvLinkStoredRepository<Course> {

    @Override
    public List<Course> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
