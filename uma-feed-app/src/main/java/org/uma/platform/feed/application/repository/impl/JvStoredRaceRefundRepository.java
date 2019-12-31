package org.uma.platform.feed.application.repository.impl;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.config.condition.StoredOpenCondition;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import org.uma.platform.jvlink.JvLinkClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JvStoredRaceRefundRepository implements JvLinkStoredRepository<RaceRefund> {

    private final JvLinkModelMapper jvLinkModelMapper;

    @Qualifier("RACE_HR")
    private final StoredOpenCondition storedOpenCondition;


    @Override
    public List<RaceRefund> readLines(LocalDateTime dateTime, Option option) {
        return JvLinkClient.readLines(storedOpenCondition, dateTime, option)
                .stream()
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .peek(model -> model.getRefundWins().removeIf(this::refundFilter))
                .peek(model -> model.getRefundPlaces().removeIf(this::refundFilter))
                .peek(model -> model.getRefundBracketQuinellas().removeIf(this::refundFilter))
                .peek(model -> model.getRefundQuinellas().removeIf(this::refundFilter))
                .peek(model -> model.getRefundQuinellaPlaces().removeIf(this::refundFilter))
                .peek(model -> model.getRefundSpares().removeIf(this::refundFilter))
                .peek(model -> model.getRefundExactas().removeIf(this::refundFilter))
                .peek(model -> model.getRefundTrios().removeIf(this::refundFilter))
                .peek(model -> model.getRefundTrifectas().removeIf(this::refundFilter))
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Stream<RaceRefund> readStream(LocalDateTime dateTime) {
        return JvLinkClient.readStream(storedOpenCondition, dateTime, Option.SETUP_WITHOUT_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .peek(model -> model.getRefundWins().removeIf(this::refundFilter))
                .peek(model -> model.getRefundPlaces().removeIf(this::refundFilter))
                .peek(model -> model.getRefundBracketQuinellas().removeIf(this::refundFilter))
                .peek(model -> model.getRefundQuinellas().removeIf(this::refundFilter))
                .peek(model -> model.getRefundQuinellaPlaces().removeIf(this::refundFilter))
                .peek(model -> model.getRefundSpares().removeIf(this::refundFilter))
                .peek(model -> model.getRefundExactas().removeIf(this::refundFilter))
                .peek(model -> model.getRefundTrios().removeIf(this::refundFilter))
                .peek(model -> model.getRefundTrifectas().removeIf(this::refundFilter));
    }

    @Override
    public Flux<RaceRefund> readFlux(LocalDateTime dateTime) {
        return JvLinkClient.readFlux(storedOpenCondition, dateTime, Option.SETUP_WITH_DIALOG)
                .map(jvStringContent -> jvLinkModelMapper
                        .deserialize(jvStringContent.getLine(), RaceRefund.class))
                .doOnNext(model -> model.getRefundWins().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundPlaces().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundBracketQuinellas().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundQuinellas().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundQuinellaPlaces().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundSpares().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundExactas().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundTrios().removeIf(this::refundFilter))
                .doOnNext(model -> model.getRefundTrifectas().removeIf(this::refundFilter));
    }

    /**
     * 不要データを削除しておく
     */
    private boolean refundFilter(RaceRefund.refund refund) {
        return refund.getBetRank() == null && refund.getRefundMoney() == null;
    }

    private boolean refundFilter(RaceRefund.refundPair refundPair) {
        return refundPair.getBetRank() == null && refundPair.getRefundMoney() == null;
    }

    private boolean refundFilter(RaceRefund.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null && refundTriplet.getRefundMoney() == null;
    }

}
