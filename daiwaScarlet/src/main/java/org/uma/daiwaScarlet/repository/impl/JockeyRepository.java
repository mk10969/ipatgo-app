package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.JockeyModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JockeyRepository implements JvLinkStoredRepository<JockeyModel> {

    @Override
    public List<JockeyModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
