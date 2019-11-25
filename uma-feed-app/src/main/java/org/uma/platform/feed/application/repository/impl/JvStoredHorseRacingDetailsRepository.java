package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.jvlink.JvLink;
import org.uma.platform.jvlink.response.JvStringContent;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class JvStoredHorseRacingDetailsRepository implements JvLinkStoredRepository<HorseRacingDetails> {

    private final JvLinkModelMapper jvLinkModelMapper;

    private final StoredOpenCondition storedOpenCondition;

    public JvStoredHorseRacingDetailsRepository(JvLinkModelMapper jvLinkModelMapper,
                                                @Qualifier("RACE_SE") StoredOpenCondition storedOpenCondition) {
        this.jvLinkModelMapper = jvLinkModelMapper;
        this.storedOpenCondition = storedOpenCondition;
    }

    @Override
    public List<HorseRacingDetails> findAll(LocalDateTime dateTime, Option option) {

        try (Stream<JvStringContent> lines = JvLink.lines(storedOpenCondition, dateTime, option)) {
            return lines
                    .map(jvContent -> jvLinkModelMapper
                            .deserialize(jvContent.getLine(), HorseRacingDetails.class))
                    .collect(ImmutableList.toImmutableList());

        }
    }

    @Override
    public Flux<HorseRacingDetails> readFlux(LocalDateTime dateTime, Option option) {
        return null;
    }

}
