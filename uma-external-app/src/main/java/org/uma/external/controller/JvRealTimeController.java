package org.uma.external.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uma.external.jvlink.JvLinkClient;

import java.util.List;

import static org.uma.external.jvlink.property.JvRealTime.OB15_HR;
import static org.uma.external.jvlink.property.JvRealTime.OB15_RA;
import static org.uma.external.jvlink.property.JvRealTime.OB15_SE;
import static org.uma.external.jvlink.property.JvRealTime.OB20_H1;
import static org.uma.external.jvlink.property.JvRealTime.OB31_O1;
import static org.uma.external.jvlink.property.JvRealTime.OB32_O2;
import static org.uma.external.jvlink.property.JvRealTime.OB33_O3;
import static org.uma.external.jvlink.property.JvRealTime.OB34_O4;
import static org.uma.external.jvlink.property.JvRealTime.OB35_O5;
import static org.uma.external.jvlink.property.JvRealTime.OB36_O6;
import static org.uma.external.jvlink.property.JvRealTime.OB41_O1;
import static org.uma.external.jvlink.property.JvRealTime.OB42_O2;


@RestController
public class JvRealTimeController extends BaseController {

    /**
     * レース情報
     */
    @GetMapping("/racingDetails")
    public List<String> findRacingDetails(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_RA.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/horseRacingDetails")
    public List<String> findHorseRacingDetails(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_SE.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/raceRefund")
    public List<String> findRaceRefund(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_HR.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/voteCount")
    public List<String> findVoteCount(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB20_H1.get(), () -> raceId)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella")
    public List<String> findWinsPlaceBracketQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB31_O1.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella")
    public List<String> findQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB32_O2.get(), () -> raceId)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace")
    public List<String> findQuinellaPlace(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB33_O3.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta")
    public List<String> findExacta(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB34_O4.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio")
    public List<String> findTrio(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB35_O5.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta")
    public List<String> findTrifecta(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB36_O6.get(), () -> raceId)
                .stream());
    }

    /**
     * 単勝・複勝・枠 時系列オッズ
     */
    @GetMapping("/timeseries/winsPlaceBracketQuinella")
    public List<String> findTimeseriesWinsPlaceBracketQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB41_O1.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬連 時系列オッズ
     */
    @GetMapping("/timeseries/quinella")
    public List<String> findTimeseriesQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB42_O2.get(), () -> raceId)
                .stream());
    }

}
