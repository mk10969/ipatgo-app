package org.uma.jvLink.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uma.jvLink.client.JvLinkClient;
import org.uma.jvLink.client.config.option.Option;
import org.uma.jvLink.client.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.uma.jvLink.client.JvStored.*;


@Controller
public class JvStoredController extends BaseController {


    @GetMapping("/racingDetails/thisWeek")
    @ResponseBody
    public List<String> findRacingDetailsOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L);
        return converter(() -> JvLinkClient
                .readLines(RACE_RA.get(), dateTime, Option.THIS_WEEK)
                .stream());
    }

    @GetMapping("/horseRacingDetails/thisWeek")
    @ResponseBody
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
    @ResponseBody
    public List<String> findRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_RA.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/horseRacingDetails/{epochSecond}")
    @ResponseBody
    public List<String> findHorseRacingDetails(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_SE.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/raceRefund/{epochSecond}")
    @ResponseBody
    public List<String> findRaceRefund(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_HR.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/voteCount/{epochSecond}")
    @ResponseBody
    public List<String> findVoteCount(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_H1.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella/{epochSecond}")
    @ResponseBody
    public List<String> findWinsPlaceBracketQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O1.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella/{epochSecond}")
    @ResponseBody
    public List<String> findQuinella(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O2.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace/{epochSecond}")
    @ResponseBody
    public List<String> findQuinellaPlace(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O3.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta/{epochSecond}")
    @ResponseBody
    public List<String> findExacta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O4.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio/{epochSecond}")
    @ResponseBody
    public List<String> findTrio(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O5.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta/{epochSecond}")
    @ResponseBody
    public List<String> findTrifecta(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(RACE_O6.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 血統情報
     */
    @GetMapping("/ancestry/{epochSecond}")
    @ResponseBody
    public List<String> findAncestry(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_BT.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/breedingHorse/{epochSecond}")
    @ResponseBody
    public List<String> findBreedingHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_HN.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/offspring/{epochSecond}")
    @ResponseBody
    public List<String> findOffspring(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(BLOD_SK.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * 競走馬、騎手、調教師、生産者、馬主
     */
    @GetMapping("/raceHorse/{epochSecond}")
    @ResponseBody
    public List<String> findRaceHorse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_UM.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/jockey/{epochSecond}")
    @ResponseBody
    public List<String> findJockey(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_KS.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/trainer/{epochSecond}")
    @ResponseBody
    public List<String> findTrainer(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_CH.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/owner/{epochSecond}")
    @ResponseBody
    public List<String> findOwner(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_BN.get(), dateTime, Option.STANDARD)
                .stream());
    }

    @GetMapping("/breeder/{epochSecond}")
    @ResponseBody
    public List<String> findBreeder(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(DIFF_BR.get(), dateTime, Option.STANDARD)
                .stream());
    }

    /**
     * コース情報
     */
    @GetMapping("/course/{epochSecond}")
    @ResponseBody
    public List<String> findCourse(@Validated @PathVariable Long epochSecond) {
        LocalDateTime dateTime = DateUtil.tolocalDateTime(epochSecond);
        return converter(() -> JvLinkClient
                .readLines(COMM_CS.get(), dateTime, Option.STANDARD)
                .stream());
    }

}
