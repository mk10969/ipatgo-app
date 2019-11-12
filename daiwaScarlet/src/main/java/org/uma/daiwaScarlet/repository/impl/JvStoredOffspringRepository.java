package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Offspring;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredOffspringRepository implements JvLinkStoredRepository<Offspring> {

    @Override
    public List<Offspring> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
