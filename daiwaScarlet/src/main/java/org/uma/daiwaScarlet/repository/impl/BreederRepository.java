package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.BreederModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class BreederRepository implements JvLinkStoredRepository<BreederModel> {


    @Override
    public List<BreederModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
