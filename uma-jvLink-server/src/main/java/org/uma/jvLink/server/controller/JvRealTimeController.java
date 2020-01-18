package org.uma.jvLink.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.uma.jvLink.core.JvLinkClient;

import java.util.List;

import static org.uma.jvLink.core.JvRealTime.*;


@Controller
public class JvRealTimeController extends BaseController {

    /**
     * レース情報
     */
    @GetMapping("/racingDetails")
    public List<byte[]> findRacingDetails(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_RA.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/horseRacingDetails")
    public List<byte[]> findHorseRacingDetails(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_SE.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/raceRefund")
    public List<byte[]> findRaceRefund(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_HR.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/voteCount")
    public List<byte[]> findVoteCount(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB20_H1.get(), () -> raceId)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella")
    public List<byte[]> findWinsPlaceBracketQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB31_O1.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella")
    public List<byte[]> findQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB32_O2.get(), () -> raceId)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace")
    public List<byte[]> findQuinellaPlace(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB33_O3.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta")
    public List<byte[]> findExacta(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB34_O4.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio")
    public List<byte[]> findTrio(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB35_O5.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta")
    public List<byte[]> findTrifecta(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB36_O6.get(), () -> raceId)
                .stream());
    }

}
