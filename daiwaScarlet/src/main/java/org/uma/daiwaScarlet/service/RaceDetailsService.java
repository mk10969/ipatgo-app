package org.uma.daiwaScarlet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.daiwaScarlet.model.RacingDetailsModel;
import org.uma.daiwaScarlet.repository.impl.RaceDetailsRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

public class RaceDetailsService {

    @Autowired
    private RaceDetailsRepository repository;

    public List<RacingDetailsModel> findAllOnThisWeek(ZonedDateTime dateTime){
        return repository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<RacingDetailsModel> findAllOnStandard(ZonedDateTime dateTime){
        return repository.findAll(dateTime, Option.STANDARD);
    }

    public List<RacingDetailsModel> findAllOnSetUpWithoutDialog(ZonedDateTime dateTime){
        return repository.findAll(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


    /**
     * ここから本気のコードですね。
     */


}
