package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLinkClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;


@Repository
@RequiredArgsConstructor
public class JvOddsWinsPlaceBracketQuinellaRepository
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
                .peek(model -> model.getWinOdds().removeIf(this::winOddsFilter))
                .peek(model -> model.getPlaceOdds().removeIf(this::placeOddsFilter))
                .peek(model -> model.getBracketQuinellaOdds().removeIf(this::bracketQuinellaOddsFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<WinsPlaceBracketQuinella> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .peek(model -> model.getWinOdds().removeIf(this::winOddsFilter))
                .peek(model -> model.getPlaceOdds().removeIf(this::placeOddsFilter))
                .peek(model -> model.getBracketQuinellaOdds().removeIf(this::bracketQuinellaOddsFilter));
    }

    @Override
    public Flux<WinsPlaceBracketQuinella> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITH_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .doOnNext(model -> model.getWinOdds().removeIf(this::winOddsFilter))
                .doOnNext(model -> model.getPlaceOdds().removeIf(this::placeOddsFilter))
                .doOnNext(model -> model.getBracketQuinellaOdds().removeIf(this::bracketQuinellaOddsFilter));
    }


    private boolean winOddsFilter(WinsPlaceBracketQuinella.WinOdds winOdds) {
        return winOdds.getOdds() == null && winOdds.getBetRank() == null;
    }

    private boolean placeOddsFilter(WinsPlaceBracketQuinella.PlaceOdds placeOdds) {
        return placeOdds.getOddsMin() == null
                && placeOdds.getOddsMax() == null
                && placeOdds.getBetRank() == null;
    }

    private boolean bracketQuinellaOddsFilter(WinsPlaceBracketQuinella.BracketQuinellaOdds bracketQuinellaOdds) {
        return bracketQuinellaOdds.getOdds() == null
                && bracketQuinellaOdds.getBetRank() == null;
    }

}
