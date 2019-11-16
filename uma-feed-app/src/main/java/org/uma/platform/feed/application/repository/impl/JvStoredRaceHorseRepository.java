package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.RaceHorse;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredRaceHorseRepository implements JvLinkStoredRepository<RaceHorse> {

    @Override
    public List<RaceHorse> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
