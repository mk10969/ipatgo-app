package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.common.model.VoteCount;
import org.uma.platform.common.utils.lang.DateUtil;
import org.uma.platform.feed.application.repository.impl.JvStoredHorseRacingDetailsRepository;
import org.uma.platform.feed.application.repository.impl.JvStoredRaceRefundRepository;
import org.uma.platform.feed.application.repository.impl.JvStoredRacingDetailsRepository;
import org.uma.platform.feed.application.repository.impl.JvStoredVoteCountRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service("RACE")
@RequiredArgsConstructor
public class JvRaceService {

    /**
     * レース詳細情報
     */
    private final JvStoredRacingDetailsRepository racingDetailsRepository;

    /**
     * レース毎 競走馬情報
     */
    private final JvStoredHorseRacingDetailsRepository horseRacingDetailsRepository;

    /**
     * レース払戻
     */
    private final JvStoredRaceRefundRepository raceRefundRepository;

    /**
     * レース投票数
     */
    private final JvStoredVoteCountRepository voteCountRepository;

    /**
     * 起動時に一度のみ評価する
     */
    private static final LocalDateTime lastWeek = DateUtil.lastWeek();


    public Flux<RacingDetails> findRacingDetails(LocalDateTime dateTime) {
        return racingDetailsRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<RacingDetails> findRacingDetailsOnThisWeek() {
        return racingDetailsRepository.readFlux(lastWeek, Option.THIS_WEEK);
    }

    public Flux<RacingDetails> findRacingDetailsOnSetUp(LocalDateTime dateTime) {
        return racingDetailsRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<HorseRacingDetails> findHorseRacingDetails(LocalDateTime dateTime) {
        return horseRacingDetailsRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<HorseRacingDetails> findHorseRacingDetailsOnThisWeek() {
        return horseRacingDetailsRepository.readFlux(lastWeek, Option.THIS_WEEK);
    }

    public Flux<HorseRacingDetails> findHorseRacingDetailsOnSetUp(LocalDateTime dateTime) {
        return horseRacingDetailsRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<RaceRefund> findRaceRefund(LocalDateTime dateTime) {
        return raceRefundRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<RaceRefund> findRaceRefundOnThisWeek() {
        return raceRefundRepository.readFlux(lastWeek, Option.THIS_WEEK);
    }

    public Flux<RaceRefund> findRaceRefundOnSetUp(LocalDateTime dateTime) {
        return raceRefundRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<VoteCount> findVoteCount(LocalDateTime dateTime) {
        return voteCountRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<VoteCount> findVoteCountOnThisWeek() {
        return voteCountRepository.readFlux(lastWeek, Option.THIS_WEEK);
    }

    public Flux<VoteCount> findVoteCountOnSetUp(LocalDateTime dateTime) {
        return voteCountRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

}
