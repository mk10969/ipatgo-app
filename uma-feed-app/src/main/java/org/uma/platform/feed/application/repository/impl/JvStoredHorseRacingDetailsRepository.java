package org.uma.platform.feed.application.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLink;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public class JvStoredHorseRacingDetailsRepository implements JvLinkStoredRepository<HorseRacingDetails> {

    private final JvLinkModelMapper jvLinkModelMapper;

    private final StoredOpenCondition storedOpenCondition;

    public JvStoredHorseRacingDetailsRepository(
            JvLinkModelMapper jvLinkModelMapper,
            @Qualifier("RACE_SE") StoredOpenCondition storedOpenCondition) {
        this.jvLinkModelMapper = jvLinkModelMapper;
        this.storedOpenCondition = storedOpenCondition;
    }


    @Override
    public Flux<HorseRacingDetails> readFlux(LocalDateTime dateTime, Option option) {
        return JvLink.readFlux(storedOpenCondition, dateTime, option)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), HorseRacingDetails.class));

    }

}
