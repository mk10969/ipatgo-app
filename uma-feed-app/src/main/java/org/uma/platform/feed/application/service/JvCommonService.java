package org.uma.platform.feed.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.Course;
import org.uma.platform.feed.application.repository.impl.JvStoredCourseRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service("COMM")
@RequiredArgsConstructor
public class JvCommonService {

    /**
     * コース情報
     */
    private final JvStoredCourseRepository courseRepository;


    public Flux<Course> findCourse(LocalDateTime dateTime) {
        return courseRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Course> findCourseOnSetUp(LocalDateTime dateTime) {
        return courseRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

}
