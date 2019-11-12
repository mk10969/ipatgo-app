package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Owner;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredOwnerRepository implements JvLinkStoredRepository<Owner> {

    @Override
    public List<Owner> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
