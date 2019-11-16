package org.uma.daiwaScarlet.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.component.JvLinkModelMapper;
import org.uma.daiwaScarlet.model.RacingDetails;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.JvLink;
import org.uma.vodka.config.Option;
import org.uma.vodka.config.condition.StoredOpenCondition;
import org.uma.vodka.response.JvStringContent;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class JvStoredRacingDetailsRepository implements JvLinkStoredRepository<RacingDetails> {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    @Qualifier("RACE_RA")
    private StoredOpenCondition storedOpenCondition;

    @Override
    public List<RacingDetails> findAll(ZonedDateTime dateTime, Option option) {
        try (Stream<JvStringContent> lines = JvLink.lines(storedOpenCondition, dateTime, option)) {
            return lines
                    .map(jvContent -> jvLinkModelMapper
                            .deserialize(jvContent.getLine(), RacingDetails.class))
                    .collect(ImmutableList.toImmutableList());
        }
    }


}
