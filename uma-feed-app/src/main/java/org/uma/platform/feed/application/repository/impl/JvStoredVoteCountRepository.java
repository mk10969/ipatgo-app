package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.VoteCount;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLinkClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredVoteCountRepository implements JvLinkStoredRepository<VoteCount> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_H1")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<VoteCount> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .peek(model -> model.getVoteCountWins().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountPlaces().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountBracketQuinellas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountQuinellas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountQuinellaPlaces().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountExactas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountTrios().removeIf(this::voteFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<VoteCount> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .peek(model -> model.getVoteCountWins().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountPlaces().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountBracketQuinellas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountQuinellas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountQuinellaPlaces().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountExactas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountTrios().removeIf(this::voteFilter));
    }

    @Override
    public Flux<VoteCount> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITH_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), VoteCount.class))
                .doOnNext(model -> model.getVoteCountWins().removeIf(this::voteFilter))
                .doOnNext(model -> model.getVoteCountPlaces().removeIf(this::voteFilter))
                .doOnNext(model -> model.getVoteCountBracketQuinellas().removeIf(this::voteFilter))
                .doOnNext(model -> model.getVoteCountQuinellas().removeIf(this::voteFilter))
                .doOnNext(model -> model.getVoteCountQuinellaPlaces().removeIf(this::voteFilter))
                .doOnNext(model -> model.getVoteCountExactas().removeIf(this::voteFilter))
                .doOnNext(model -> model.getVoteCountTrios().removeIf(this::voteFilter));
    }

    /**
     * 不要データを削除しておく
     */
    private boolean voteFilter(VoteCount.Vote vote) {
        return vote.getBetRank() == null && vote.getVoteCount() == null;
    }

    private boolean voteFilter(VoteCount.VotePair votePair) {
        return votePair.getBetRank() == null && votePair.getVoteCount() == null;
    }

    private boolean voteFilter(VoteCount.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null && voteTriplet.getVoteCount() == null;
    }

}
