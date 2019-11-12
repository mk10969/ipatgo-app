package org.uma.daiwaScarlet.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.component.JvLinkModelMapper;
import org.uma.daiwaScarlet.model.RaceRefund;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.JvLink;
import org.uma.vodka.config.Option;
import org.uma.vodka.config.condition.StoredOpenCondition;
import org.uma.vodka.response.JvStringContent;

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
                    .map(jvContent ->
                            jvLinkModelMapper.deserialize(jvContent.getLine(), RaceRefund.class)
                    )
                    .collect(ImmutableList.toImmutableList());
        }
    }

}
