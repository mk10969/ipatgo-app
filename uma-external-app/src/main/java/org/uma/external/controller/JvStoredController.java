package org.uma.external.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.uma.external.jvlink.JvLinkClient;
import org.uma.external.jvlink.config.option.Option;
import org.uma.external.jvlink.util.DateUtil;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

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
    public Flux<String> findRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return getAll(() -> JvLinkClient.readLines(RACE_RA.get(), dateTime, Option.THIS_WEEK));
    }

    @GetMapping("/horseRacingDetails/thisWeek")
    public Flux<String> findHorseRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return getAll(() -> JvLinkClient.readLines(RACE_SE.get(), dateTime, Option.THIS_WEEK));
    }


    /**
     * レース情報
     */
    @GetMapping("/racingDetails/{epochSecond}")
    public Flux<String> findRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_RA.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/horseRacingDetails/{epochSecond}")
    public Flux<String> findHorseRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_SE.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/raceRefund/{epochSecond}")
    public Flux<String> findRaceRefund(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_HR.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/voteCount/{epochSecond}")
    public Flux<String> findVoteCount(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_H1.get(), dateTime, Option.STANDARD));
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella/{epochSecond}")
    public Flux<String> findWinsPlaceBracketQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_O1.get(), dateTime, Option.STANDARD));
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella/{epochSecond}")
    public Flux<String> findQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_O2.get(), dateTime, Option.STANDARD));
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace/{epochSecond}")
    public Flux<String> findQuinellaPlace(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_O3.get(), dateTime, Option.STANDARD));
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta/{epochSecond}")
    public Flux<String> findExacta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_O4.get(), dateTime, Option.STANDARD));
    }

    /**
     * 三連複
     */
    @GetMapping("/trio/{epochSecond}")
    public Flux<String> findTrio(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_O5.get(), dateTime, Option.STANDARD));
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta/{epochSecond}")
    public Flux<String> findTrifecta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(RACE_O6.get(), dateTime, Option.STANDARD));
    }

    /**
     * 血統情報
     */
    @GetMapping("/ancestry/{epochSecond}")
    public Flux<String> findAncestry(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(BLOD_BT.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/breedingHorse/{epochSecond}")
    public Flux<String> findBreedingHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(BLOD_HN.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/offspring/{epochSecond}")
    public Flux<String> findOffspring(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(BLOD_SK.get(), dateTime, Option.STANDARD));
    }

    /**
     * 競走馬、騎手、調教師、生産者、馬主
     */
    @GetMapping("/raceHorse/{epochSecond}")
    public Flux<String> findRaceHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(DIFF_UM.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/jockey/{epochSecond}")
    public Flux<String> findJockey(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(DIFF_KS.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/trainer/{epochSecond}")
    public Flux<String> findTrainer(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(DIFF_CH.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/owner/{epochSecond}")
    public Flux<String> findOwner(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(DIFF_BN.get(), dateTime, Option.STANDARD));
    }

    @GetMapping("/breeder/{epochSecond}")
    public Flux<String> findBreeder(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(DIFF_BR.get(), dateTime, Option.STANDARD));
    }

    /**
     * コース情報
     */
    @GetMapping("/course/{epochSecond}")
    public Flux<String> findCourse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.toLocalDateTime(epochSecond);
        return getAll(() -> JvLinkClient.readLines(COMM_CS.get(), dateTime, Option.STANDARD));
    }

}
