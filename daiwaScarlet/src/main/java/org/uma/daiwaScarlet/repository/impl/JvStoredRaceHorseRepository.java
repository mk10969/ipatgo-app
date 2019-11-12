package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.RaceHorse;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredRaceHorseRepository implements JvLinkStoredRepository<RaceHorse> {

    @Override
    public List<RaceHorse> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
