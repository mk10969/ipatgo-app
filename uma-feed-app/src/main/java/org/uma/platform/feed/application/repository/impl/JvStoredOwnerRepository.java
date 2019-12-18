package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.Owner;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLink;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class JvStoredOwnerRepository implements JvLinkStoredRepository<Owner> {

    private final JvLinkModelMapper jvLinkModelMapper;

    private final StoredOpenCondition storedOpenCondition;

    public JvStoredOwnerRepository(
            JvLinkModelMapper jvLinkModelMapper,
            @Qualifier("DIFF_BN") StoredOpenCondition storedOpenCondition) {
        this.jvLinkModelMapper = jvLinkModelMapper;
        this.storedOpenCondition = storedOpenCondition;
    }


    @Override
    public List<Owner> readLines(LocalDateTime dateTime, Option option) {
        return JvLink.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Owner.class))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<Owner> readStream(LocalDateTime dateTime) {
        return JvLink.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), Owner.class));
    }

}
