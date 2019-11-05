package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.OffspringModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OffspringRepository implements JvLinkStoredRepository<OffspringModel> {

    @Override
    public List<OffspringModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
