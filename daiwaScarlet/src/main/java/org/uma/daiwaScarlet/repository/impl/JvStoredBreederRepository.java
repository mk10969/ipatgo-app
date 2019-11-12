package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.Breeder;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredBreederRepository implements JvLinkStoredRepository<Breeder> {


    @Override
    public List<Breeder> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
