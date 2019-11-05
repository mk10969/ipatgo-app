package org.uma.daiwaScarlet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uma.daiwaScarlet.model.RacingDetailsModel;
import org.uma.daiwaScarlet.repository.impl.RacingDetailsRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;


@Service
public class RacingDetailsService {

    @Autowired
    private RacingDetailsRepository repository;

    public List<RacingDetailsModel> findAllOnThisWeek(ZonedDateTime dateTime) {
        return repository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<RacingDetailsModel> findAllOnStandard(ZonedDateTime dateTime) {
        return repository.findAll(dateTime, Option.STANDARD);
    }

    public List<RacingDetailsModel> findAllOnSetUpWithoutDialog(ZonedDateTime dateTime) {
        return repository.findAll(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


    /**
     * ここから本気のコードですね。
     */


}
