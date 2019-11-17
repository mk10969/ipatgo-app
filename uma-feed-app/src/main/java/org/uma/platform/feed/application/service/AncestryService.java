package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.Ancestry;
import org.uma.platform.feed.application.repository.impl.JvStoredAncestryRepository;

import java.time.ZonedDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AncestryService {

    private final JvStoredAncestryRepository jvRepository;


    public List<Ancestry> findAllOnThisWeek(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<Ancestry> findAllOnStandard(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.STANDARD);
    }

}

