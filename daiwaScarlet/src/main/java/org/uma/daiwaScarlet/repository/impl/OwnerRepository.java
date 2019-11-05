package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.OwnerModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OwnerRepository implements JvLinkStoredRepository<OwnerModel> {

    @Override
    public List<OwnerModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
