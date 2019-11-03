package org.uma.daiwaScarlet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.uma.daiwaScarlet.model.RaceDetailsModel;
import org.uma.daiwaScarlet.repository.impl.RaceDetailsRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

public class RaceDetailsService {

    @Autowired
    private RaceDetailsRepository repository;

    public List<RaceDetailsModel> findAllOnThisWeek(ZonedDateTime dateTime){
        return repository.findAll(dateTime, Option.THIS_WEEK);
    }

    public List<RaceDetailsModel> findAllOnStandard(ZonedDateTime dateTime){
        return repository.findAll(dateTime, Option.STANDARD);
    }

    public List<RaceDetailsModel> findAllOnSetUpWithoutDialog(ZonedDateTime dateTime){
        return repository.findAll(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


    /**
     * ここから本気のコードですね。
     */


}
