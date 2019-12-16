package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.VoteCount;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLink;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JvStoredVoteCountRepository implements JvLinkStoredRepository<VoteCount> {

    private final JvLinkModelMapper jvLinkModelMapper;

    private final StoredOpenCondition storedOpenCondition;

    public JvStoredVoteCountRepository(
            JvLinkModelMapper jvLinkModelMapper,
            @Qualifier("RACE_H1") StoredOpenCondition storedOpenCondition) {
        this.jvLinkModelMapper = jvLinkModelMapper;
        this.storedOpenCondition = storedOpenCondition;
    }


    @Override
    public List<VoteCount> readLine(LocalDateTime dateTime, Option option) {
        return JvLink.lines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .collect(ImmutableList.toImmutableList());
    }

}
