package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.RealTimeKey;
import org.uma.platform.common.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkRealTimeRepository;
import org.uma.platform.feed.application.repository.JvLinkRepository;
import org.uma.platform.jvlink.JvLinkClient;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JvRtRaceRefundRepository implements JvLinkRealTimeRepository<RaceRefund> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("0B15_HR")
    private final RealTimeOpenCondition realTimeOpenCondition;


    @Override
    public List<RaceRefund> readLine(RealTimeKey realTimeKey) {
        return JvLinkClient.readLines(realTimeOpenCondition, realTimeKey)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .peek(model -> model.getRefundWins().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundPlaces().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundBracketQuinellas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundQuinellas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundQuinellaPlaces().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundSpares().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundExactas().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundTrios().removeIf(JvLinkRepository::refundFilter))
                .peek(model -> model.getRefundTrifectas().removeIf(JvLinkRepository::refundFilter))
                .collect(ImmutableList.toImmutableList());
    }

}
