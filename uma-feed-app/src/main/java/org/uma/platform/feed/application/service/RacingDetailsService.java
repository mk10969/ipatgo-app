package org.uma.platform.feed.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.platform.feed.application.model.RacingDetails;
import org.uma.platform.feed.application.repository.impl.JvStoredRacingDetailsRepository;
import org.uma.platform.bean.config.Option;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;
import java.util.List;


@Service
public class RacingDetailsService {

    @Autowired
    private JvStoredRacingDetailsRepository jvRepository;

//    @Autowired
//    private ReactiveRacingDetailsRepository mongoRepository;

    public List<RacingDetails> findAllOnThisWeek(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<RacingDetails> findAllOnStandard(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.STANDARD);
    }

    public List<RacingDetails> findAllOnSetUpWithoutDialog(ZonedDateTime dateTime) {
        return jvRepository.findAll(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

//    public Flux<RacingDetails> doInsertBatch(ZonedDateTime dateTime) {
//        return mongoRepository.insert(findAllOnThisWeek(dateTime));
//    }


}
