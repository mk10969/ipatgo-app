package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.Jockey;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredJockeyRepository implements JvLinkStoredRepository<Jockey> {

    @Override
    public List<Jockey> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
