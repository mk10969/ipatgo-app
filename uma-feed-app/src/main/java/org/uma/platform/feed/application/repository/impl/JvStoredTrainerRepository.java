package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.Trainer;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredTrainerRepository implements JvLinkStoredRepository<Trainer> {

    @Override
    public List<Trainer> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
