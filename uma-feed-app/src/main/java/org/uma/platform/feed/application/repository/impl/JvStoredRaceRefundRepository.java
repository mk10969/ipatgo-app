package org.uma.platform.feed.application.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.Course;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLink;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public class JvStoredRaceRefundRepository implements JvLinkStoredRepository<RaceRefund> {

    private final JvLinkModelMapper jvLinkModelMapper;

    private final StoredOpenCondition storedOpenCondition;

    public JvStoredRaceRefundRepository(
            JvLinkModelMapper jvLinkModelMapper,
            @Qualifier("RACE_HR") StoredOpenCondition storedOpenCondition) {
        this.jvLinkModelMapper = jvLinkModelMapper;
        this.storedOpenCondition = storedOpenCondition;
    }


    @Override
    public Flux<RaceRefund> readFlux(LocalDateTime dateTime, Option option) {
        return JvLink.readFlux(storedOpenCondition, dateTime, option)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class));

    }

}
