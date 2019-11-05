package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.RaceHorseModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class RaceHorseRepository implements JvLinkStoredRepository<RaceHorseModel> {

    @Override
    public List<RaceHorseModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
