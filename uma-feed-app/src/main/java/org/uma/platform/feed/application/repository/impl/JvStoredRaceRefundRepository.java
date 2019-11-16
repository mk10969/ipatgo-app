package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.model.RaceRefund;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.bean.JvLink;
import org.uma.platform.bean.config.Option;
import org.uma.platform.bean.config.condition.StoredOpenCondition;
import org.uma.platform.bean.response.JvStringContent;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class JvStoredRaceRefundRepository implements JvLinkStoredRepository<RaceRefund> {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    @Qualifier("RACE_HR")
    private StoredOpenCondition storedOpenCondition;

    @Override
    public List<RaceRefund> findAll(ZonedDateTime dateTime, Option option) {

        try (Stream<JvStringContent> lines = JvLink.lines(storedOpenCondition, dateTime, option)) {
            return lines
                    .map(jvContent -> jvLinkModelMapper
                            .deserialize(jvContent.getLine(), RaceRefund.class))
                    .collect(ImmutableList.toImmutableList());
        }
    }

}
