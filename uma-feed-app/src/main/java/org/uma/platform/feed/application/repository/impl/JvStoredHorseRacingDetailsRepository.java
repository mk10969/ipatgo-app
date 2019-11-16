package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.model.HorseRacingDetails;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.JvLink;
import org.uma.platform.bean.config.Option;
import org.uma.platform.bean.config.condition.StoredOpenCondition;
import org.uma.platform.bean.response.JvStringContent;

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
                    .map(jvContent -> jvLinkModelMapper
                            .deserialize(jvContent.getLine(), HorseRacingDetails.class))
                    .collect(ImmutableList.toImmutableList());

        }
    }


}

