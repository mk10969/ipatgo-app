package org.uma.platform.feed.application.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.model.Breeder;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class JvStoredBreederRepository implements JvLinkStoredRepository<Breeder> {


    @Override
    public List<Breeder> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
