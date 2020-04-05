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
     * レース詳細情報
     */
    @GetMapping("/racingDetails")
    public String findRacingDetails(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB15_RA.get(), () -> raceId)
                .stream());
    }

    /**
     * レース払戻
     */
    @GetMapping("/raceRefund")
    public String findRaceRefund(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB15_HR.get(), () -> raceId)
                .stream());
    }

    /**
     * レース票数
     */
    @GetMapping("/voteCount")
    public String mono(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB20_H1.get(), () -> raceId)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella")
    public String findWinsPlaceBracketQuinella(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB31_O1.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella")
    public String findQuinella(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB32_O2.get(), () -> raceId)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace")
    public String findQuinellaPlace(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB33_O3.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta")
    public String findExacta(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB34_O4.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio")
    public String findTrio(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB35_O5.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta")
    public String findTrifecta(@RequestParam("raceId") String raceId) {
        return getOne(() -> JvLinkClient
                .readLines(OB36_O6.get(), () -> raceId)
                .stream());
    }

    /**
     * 競走馬ごとのレース詳細
     */
    @GetMapping("/horseRacingDetails")
    public List<String> findHorseRacingDetails(@RequestParam("raceId") String raceId) {
        return getAll(() -> JvLinkClient
                .readLines(OB15_SE.get(), () -> raceId)
                .stream());
    }

    /**
     * 単勝・複勝・枠 時系列オッズ
     */
    @GetMapping("/timeseries/winsPlaceBracketQuinella")
    public List<String> findTimeseriesWinsPlaceBracketQuinella(@RequestParam("raceId") String raceId) {
        return getAll(() -> JvLinkClient
                .readLines(OB41_O1.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬連 時系列オッズ
     */
    @GetMapping("/timeseries/quinella")
    public List<String> findTimeseriesQuinella(@RequestParam("raceId") String raceId) {
        return getAll(() -> JvLinkClient
                .readLines(OB42_O2.get(), () -> raceId)
                .stream());
    }

}
