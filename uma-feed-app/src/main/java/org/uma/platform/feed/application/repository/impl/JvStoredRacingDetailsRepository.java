package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.bean.JvLink;
import org.uma.platform.bean.response.JvStringContent;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredRacingDetailsRepository implements JvLinkStoredRepository<RacingDetails> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_RA")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<RacingDetails> findAll(ZonedDateTime dateTime, Option option) {
        try (Stream<JvStringContent> lines = JvLink.lines(storedOpenCondition, dateTime, option)) {
            return lines
                    .map(jvContent -> jvLinkModelMapper
                            .deserialize(jvContent.getLine(), RacingDetails.class))
                    .collect(ImmutableList.toImmutableList());
        }
    }


}
