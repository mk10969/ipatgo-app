package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.RaceRefundModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class RaceRefundRepository implements JvLinkStoredRepository<RaceRefundModel> {

    @Override
    public List<RaceRefundModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
