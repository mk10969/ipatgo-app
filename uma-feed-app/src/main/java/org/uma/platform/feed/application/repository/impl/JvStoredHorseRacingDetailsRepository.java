package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLinkClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
    public List<HorseRacingDetails> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), HorseRacingDetails.class))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<HorseRacingDetails> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), HorseRacingDetails.class));
    }

}
