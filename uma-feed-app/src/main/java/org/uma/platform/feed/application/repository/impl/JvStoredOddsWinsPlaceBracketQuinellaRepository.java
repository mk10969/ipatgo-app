package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;
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
public class JvStoredOddsWinsPlaceBracketQuinellaRepository
        implements JvLinkStoredRepository<WinsPlaceBracketQuinella> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_O1")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<WinsPlaceBracketQuinella> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .peek(model -> model.getWinOdds().removeIf(JvLinkRepository::winOddsFilter))
                .peek(model -> model.getPlaceOdds().removeIf(JvLinkRepository::placeOddsFilter))
                .peek(model -> model.getBracketQuinellaOdds().removeIf(JvLinkRepository::bracketQuinellaOddsFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<WinsPlaceBracketQuinella> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .peek(model -> model.getWinOdds().removeIf(JvLinkRepository::winOddsFilter))
                .peek(model -> model.getPlaceOdds().removeIf(JvLinkRepository::placeOddsFilter))
                .peek(model -> model.getBracketQuinellaOdds().removeIf(JvLinkRepository::bracketQuinellaOddsFilter));
    }

    @Override
    public Flux<WinsPlaceBracketQuinella> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .doOnNext(model -> model.getWinOdds().removeIf(JvLinkRepository::winOddsFilter))
                .doOnNext(model -> model.getPlaceOdds().removeIf(JvLinkRepository::placeOddsFilter))
                .doOnNext(model -> model.getBracketQuinellaOdds().removeIf(JvLinkRepository::bracketQuinellaOddsFilter));
    }

}
