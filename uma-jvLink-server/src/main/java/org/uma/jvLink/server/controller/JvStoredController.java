package org.uma.jvLink.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.core.config.option.Option;
import org.uma.jvLink.core.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.uma.jvLink.core.JvStored.*;


@Controller
public class JvStoredController extends BaseController {


    @GetMapping("/racingDetails/thisWeek")
    public List<byte[]> findRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return converter(() -> JvLinkClient
                .readLines(RACE_RA.get(), dateTime, Option.THIS_WEEK)
                .stream());
    }

    @GetMapping("/horseRacingDetails/thisWeek")
    public List<byte[]> findHorseRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return converter(() -> JvLinkClient
                .readLines(RACE_SE.get(), dateTime, Option.THIS_WEEK)
                .stream());
    }


    /**
     * レース情報
     */
    @GetMapping("/racingDetails/{epochSecond}")
    public List<byte[]> findRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_RA.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/horseRacingDetails/{epochSecond}")
    public List<byte[]> findHorseRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_SE.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/raceRefund/{epochSecond}")
    public List<byte[]> findRaceRefund(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_HR.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/voteCount/{epochSecond}")
    public List<byte[]> findVoteCount(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_H1.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella/{epochSecond}")
    public List<byte[]> findWinsPlaceBracketQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O1.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella/{epochSecond}")
    public List<byte[]> findQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O2.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace/{epochSecond}")
    public List<byte[]> findQuinellaPlace(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O3.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta/{epochSecond}")
    public List<byte[]> findExacta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O4.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio/{epochSecond}")
    public List<byte[]> findTrio(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O5.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta/{epochSecond}")
    public List<byte[]> findTrifecta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O6.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 血統情報
     */
    @GetMapping("/ancestry/{epochSecond}")
    public List<byte[]> findAncestry(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_BT.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/breedingHorse/{epochSecond}")
    public List<byte[]> findBreedingHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_HN.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/offspring/{epochSecond}")
    public List<byte[]> findOffspring(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_SK.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 競走馬、騎手、調教師、生産者、馬主
     */
    @GetMapping("/raceHorse/{epochSecond}")
    public List<byte[]> findRaceHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_UM.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/jockey/{epochSecond}")
    public List<byte[]> findJockey(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_KS.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/trainer/{epochSecond}")
    public List<byte[]> findTrainer(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_CH.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/owner/{epochSecond}")
    public List<byte[]> findOwner(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_BN.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/breeder/{epochSecond}")
    public List<byte[]> findBreeder(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_BR.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * コース情報
     */
    @GetMapping("/course/{epochSecond}")
    public List<byte[]> findCourse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(COMM_CS.get(), dateTime, Option.STANDARD)
                .stream());
    }

}
