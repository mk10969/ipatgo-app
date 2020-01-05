package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.RealTimeKey;
import org.uma.platform.common.config.condition.RealTimeOpenCondition;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkRealTimeRepository;
import org.uma.platform.jvlink.JvLinkClient;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class JvRtHorseRacingDetailsRepository
        implements JvLinkRealTimeRepository<HorseRacingDetails> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("0B15_SE")
    private final RealTimeOpenCondition realTimeOpenCondition;


    @Override
    public List<HorseRacingDetails> readLine(RealTimeKey realTimeKey) {
        return JvLinkClient.readLines(realTimeOpenCondition, realTimeKey)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), HorseRacingDetails.class))
                .collect(ImmutableList.toImmutableList());

    }
}
