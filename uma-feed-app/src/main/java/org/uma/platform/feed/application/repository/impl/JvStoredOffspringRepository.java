package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.Offspring;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredOffspringRepository implements JvLinkStoredRepository<Offspring> {

    @Override
    public List<Offspring> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
