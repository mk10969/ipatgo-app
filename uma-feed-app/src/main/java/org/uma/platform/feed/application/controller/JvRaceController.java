package org.uma.platform.feed.application.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/race")
@RequiredArgsConstructor
public class JvRaceController {

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


    public List<RacingDetails> findRacingDetails(LocalDateTime dateTime) {
        return racingDetailsRepository.readLine(dateTime, Option.STANDARD);
    }

    public List<RacingDetails> findRacingDetailsOnThisWeek() {
        return racingDetailsRepository.readLine(lastWeek, Option.THIS_WEEK);
    }


    public List<HorseRacingDetails> findHorseRacingDetails(LocalDateTime dateTime) {
        return horseRacingDetailsRepository.readLine(dateTime, Option.STANDARD);
    }

    public List<HorseRacingDetails> findHorseRacingDetailsOnThisWeek() {
        return horseRacingDetailsRepository.readLine(lastWeek, Option.THIS_WEEK);
    }


    public List<RaceRefund> findRaceRefund(LocalDateTime dateTime) {
        return raceRefundRepository.readLine(dateTime, Option.STANDARD);
    }

    public List<RaceRefund> findRaceRefundOnThisWeek() {
        return raceRefundRepository.readLine(lastWeek, Option.THIS_WEEK);
    }


    public List<VoteCount> findVoteCount(LocalDateTime dateTime) {
        return voteCountRepository.readLine(dateTime, Option.STANDARD);
    }

    public List<VoteCount> findVoteCountOnThisWeek() {
        return voteCountRepository.readLine(lastWeek, Option.THIS_WEEK);
    }

}
