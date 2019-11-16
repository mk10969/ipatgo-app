package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.repository.impl.JvStoredRacingDetailsRepository;

import java.time.ZonedDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RacingDetailsService {

    private final JvStoredRacingDetailsRepository jvRepository;


    public List<RacingDetails> findAllOnThisWeek(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<RacingDetails> findAllOnStandard(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.STANDARD);
    }

    public List<RacingDetails> findAllOnSetUpWithoutDialog(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


}
