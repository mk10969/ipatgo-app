package org.uma.external.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.uma.external.jvlink.JvLinkClient;
import org.uma.external.jvlink.config.option.Option;
import org.uma.external.jvlink.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.uma.external.jvlink.property.JvStored.BLOD_BT;
import static org.uma.external.jvlink.property.JvStored.BLOD_HN;
import static org.uma.external.jvlink.property.JvStored.BLOD_SK;
import static org.uma.external.jvlink.property.JvStored.COMM_CS;
import static org.uma.external.jvlink.property.JvStored.DIFF_BN;
import static org.uma.external.jvlink.property.JvStored.DIFF_BR;
import static org.uma.external.jvlink.property.JvStored.DIFF_CH;
import static org.uma.external.jvlink.property.JvStored.DIFF_KS;
import static org.uma.external.jvlink.property.JvStored.DIFF_UM;
import static org.uma.external.jvlink.property.JvStored.RACE_H1;
import static org.uma.external.jvlink.property.JvStored.RACE_HR;
import static org.uma.external.jvlink.property.JvStored.RACE_O1;
import static org.uma.external.jvlink.property.JvStored.RACE_O2;
import static org.uma.external.jvlink.property.JvStored.RACE_O3;
import static org.uma.external.jvlink.property.JvStored.RACE_O4;
import static org.uma.external.jvlink.property.JvStored.RACE_O5;
import static org.uma.external.jvlink.property.JvStored.RACE_O6;
import static org.uma.external.jvlink.property.JvStored.RACE_RA;
import static org.uma.external.jvlink.property.JvStored.RACE_SE;


@RestController
public class JvStoredController extends BaseController {


    @GetMapping("/racingDetails/thisWeek")
    public List<String> findRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return converter(() -> JvLinkClient
                .readLines(RACE_RA.get(), dateTime, Option.THIS_WEEK)
                .stream());
    }

    @GetMapping("/horseRacingDetails/thisWeek")
    public List<String> findHorseRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return converter(() -> JvLinkClient
                .readLines(RACE_SE.get(), dateTime, Option.THIS_WEEK)
                .stream());
    }


    /**
     * レース情報
     */
    @GetMapping("/racingDetails/{epochSecond}")
    public List<String> findRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_RA.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/horseRacingDetails/{epochSecond}")
    public List<String> findHorseRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_SE.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/raceRefund/{epochSecond}")
    public List<String> findRaceRefund(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_HR.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/voteCount/{epochSecond}")
    public List<String> findVoteCount(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_H1.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella/{epochSecond}")
    public List<String> findWinsPlaceBracketQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O1.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella/{epochSecond}")
    public List<String> findQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O2.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace/{epochSecond}")
    public List<String> findQuinellaPlace(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O3.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta/{epochSecond}")
    public List<String> findExacta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O4.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio/{epochSecond}")
    public List<String> findTrio(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O5.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta/{epochSecond}")
    public List<String> findTrifecta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O6.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 血統情報
     */
    @GetMapping("/ancestry/{epochSecond}")
    public List<String> findAncestry(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_BT.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/breedingHorse/{epochSecond}")
    public List<String> findBreedingHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_HN.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/offspring/{epochSecond}")
    public List<String> findOffspring(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_SK.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 競走馬、騎手、調教師、生産者、馬主
     */
    @GetMapping("/raceHorse/{epochSecond}")
    public List<String> findRaceHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_UM.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/jockey/{epochSecond}")
    public List<String> findJockey(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_KS.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/trainer/{epochSecond}")
    public List<String> findTrainer(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_CH.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/owner/{epochSecond}")
    public List<String> findOwner(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_BN.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/breeder/{epochSecond}")
    public List<String> findBreeder(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_BR.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * コース情報
     */
    @GetMapping("/course/{epochSecond}")
    public List<String> findCourse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(COMM_CS.get(), dateTime, Option.STANDARD)
                .stream());
    }

}
