package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Ancestry;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredAncestryRepository implements JvLinkStoredRepository<Ancestry> {


    @Override
    public List<Ancestry> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
