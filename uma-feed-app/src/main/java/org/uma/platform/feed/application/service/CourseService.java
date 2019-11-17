package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.Course;
import org.uma.platform.feed.application.repository.impl.JvStoredCourseRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final JvStoredCourseRepository jvRepository;


    public List<Course> findAllOnThisWeek(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<Course> findAllOnStandard(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.STANDARD);
    }

}
