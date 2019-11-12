package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Trainer;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredTrainerRepository implements JvLinkStoredRepository<Trainer> {

    @Override
    public List<Trainer> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
