package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.AncestryModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class AncestryRepository implements JvLinkStoredRepository<AncestryModel> {


    @Override
    public List<AncestryModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
