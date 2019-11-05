package org.uma.daiwaScarlet.repository.impl;

import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.model.BreedingHorseModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class BreedingHorseRepository implements JvLinkStoredRepository<BreedingHorseModel> {


    @Override
    public List<BreedingHorseModel> findAll(ZonedDateTime time, Option option) {
        return null;
    }

}
