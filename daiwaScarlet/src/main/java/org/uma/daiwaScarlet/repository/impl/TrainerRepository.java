package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.TrainerModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class TrainerRepository implements JvLinkStoredRepository<TrainerModel> {

    @Override
    public List<TrainerModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
