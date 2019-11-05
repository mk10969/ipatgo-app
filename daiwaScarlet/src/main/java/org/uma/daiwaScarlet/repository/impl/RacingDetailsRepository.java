package org.uma.daiwaScarlet.repository.impl;


import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.daiwaScarlet.component.JvLinkModelMapper;
import org.uma.daiwaScarlet.model.RacingDetailsModel;
import org.uma.daiwaScarlet.repository.JvLinkStoredRepository;
import org.uma.vodka.JvLink;
import org.uma.vodka.config.Option;
import org.uma.vodka.config.condition.StoredOpenCondition;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class RacingDetailsRepository implements JvLinkStoredRepository<RacingDetailsModel> {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    @Qualifier("RACE_RA")
    private StoredOpenCondition storedOpenCondition;

    public List<RacingDetailsModel> findAll(ZonedDateTime dateTime, Option option) {
        return JvLink.lines(storedOpenCondition, dateTime, option)
                .map(jvContent -> jvLinkModelMapper.deserialize(jvContent.getLine(), RacingDetailsModel.class))
                .collect(ImmutableList.toImmutableList());
    }


}
