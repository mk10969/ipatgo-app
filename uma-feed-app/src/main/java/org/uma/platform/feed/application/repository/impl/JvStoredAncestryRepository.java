package org.uma.platform.feed.application.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.Ancestry;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLink;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public class JvStoredAncestryRepository implements JvLinkStoredRepository<Ancestry> {

    private final JvLinkModelMapper jvLinkModelMapper;

    private final StoredOpenCondition storedOpenCondition;

    public JvStoredAncestryRepository(
            JvLinkModelMapper jvLinkModelMapper,
            @Qualifier("BLOD_BT") StoredOpenCondition storedOpenCondition) {
        this.jvLinkModelMapper = jvLinkModelMapper;
        this.storedOpenCondition = storedOpenCondition;
    }

    @Override
    public Flux<Ancestry> readFlux(LocalDateTime dateTime, Option option) {
        return JvLink.readFlux(storedOpenCondition, dateTime, option)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Ancestry.class));
    }

}
