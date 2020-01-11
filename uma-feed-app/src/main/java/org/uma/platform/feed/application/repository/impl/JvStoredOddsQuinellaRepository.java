package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.odds.Quinella;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkRepository;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLinkClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;


@Repository
@RequiredArgsConstructor
public class JvStoredOddsQuinellaRepository implements JvLinkStoredRepository<Quinella> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_O2")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<Quinella> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Quinella.class))
                .peek(model -> model.getQuinellaOdds().removeIf(JvLinkRepository::quinellaOddsFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<Quinella> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Quinella.class))
                .peek(model -> model.getQuinellaOdds().removeIf(JvLinkRepository::quinellaOddsFilter));
    }

    @Override
    public Flux<Quinella> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Quinella.class))
                .doOnNext(model -> model.getQuinellaOdds().removeIf(JvLinkRepository::quinellaOddsFilter));
    }

}
