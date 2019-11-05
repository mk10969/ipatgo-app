package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.HorseRacingDetailsModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class HorseRacingDetailsRepository implements JvLinkStoredRepository<HorseRacingDetailsModel> {


    @Override
    public List<HorseRacingDetailsModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
