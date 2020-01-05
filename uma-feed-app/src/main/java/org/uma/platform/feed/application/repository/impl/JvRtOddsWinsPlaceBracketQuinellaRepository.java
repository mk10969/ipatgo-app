package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.RealTimeKey;
import org.uma.platform.common.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkRealTimeRepository;
import org.uma.platform.feed.application.repository.JvLinkRepository;
import org.uma.platform.jvlink.JvLinkClient;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JvRtOddsWinsPlaceBracketQuinellaRepository
        implements JvLinkRealTimeRepository<WinsPlaceBracketQuinella> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("0B31_O1")
    private final RealTimeOpenCondition realTimeOpenCondition;


    @Override
    public List<WinsPlaceBracketQuinella> readLine(RealTimeKey realTimeKey) {
        return JvLinkClient.readLines(realTimeOpenCondition, realTimeKey)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), WinsPlaceBracketQuinella.class))
                .peek(model -> model.getWinOdds().removeIf(JvLinkRepository::winOddsFilter))
                .peek(model -> model.getPlaceOdds().removeIf(JvLinkRepository::placeOddsFilter))
                .peek(model -> model.getBracketQuinellaOdds().removeIf(JvLinkRepository::bracketQuinellaOddsFilter))
                .collect(ImmutableList.toImmutableList());
    }
    
}
