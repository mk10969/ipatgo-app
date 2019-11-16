package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.Ancestry;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredAncestryRepository implements JvLinkStoredRepository<Ancestry> {


    @Override
    public List<Ancestry> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
