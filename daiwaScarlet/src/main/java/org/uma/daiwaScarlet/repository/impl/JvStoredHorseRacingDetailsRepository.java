package org.uma.daiwaScarlet.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.component.JvLinkModelMapper;
import org.uma.daiwaScarlet.model.HorseRacingDetails;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.JvLink;
import org.uma.vodka.config.Option;
import org.uma.vodka.config.condition.StoredOpenCondition;
import org.uma.vodka.response.JvStringContent;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class JvStoredHorseRacingDetailsRepository implements JvLinkStoredRepository<HorseRacingDetails> {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    @Qualifier("RACE_SE")
    private StoredOpenCondition storedOpenCondition;

    @Override
    public List<HorseRacingDetails> findAll(ZonedDateTime dateTime, Option option) {

        try (Stream<JvStringContent> lines = JvLink.lines(storedOpenCondition, dateTime, option)) {
            return lines
                    .map(jvContent ->
                            jvLinkModelMapper.deserialize(jvContent.getLine(), HorseRacingDetails.class)
                    )
                    .collect(ImmutableList.toImmutableList());

        }
    }


}

