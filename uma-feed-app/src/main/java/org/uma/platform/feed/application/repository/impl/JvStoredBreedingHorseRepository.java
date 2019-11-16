package org.uma.platform.feed.application.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.BreedingHorse;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JvStoredBreedingHorseRepository implements JvLinkStoredRepository<BreedingHorse> {

    private final JvLinkModelMapper jvLinkModelMapper;


    @Override
    public List<BreedingHorse> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
