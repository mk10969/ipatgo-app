package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.feed.application.repository.impl.JvStoredHorseRacingDetailsRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorseRacingDetailsService {

    private final JvStoredHorseRacingDetailsRepository jvRepository;


    public List<HorseRacingDetails> findAllOnThisWeek(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<HorseRacingDetails> findAllOnStandard(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.STANDARD);
    }

}

