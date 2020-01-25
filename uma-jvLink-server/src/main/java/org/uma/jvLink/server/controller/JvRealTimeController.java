package org.uma.jvLink.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uma.jvLink.client.JvLinkClient;

import java.util.List;

import static org.uma.jvLink.client.JvRealTime.*;


@Controller
public class JvRealTimeController extends BaseController {

    /**
     * レース情報
     */
    @GetMapping("/racingDetails")
    @ResponseBody
    public List<String> findRacingDetails(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_RA.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/horseRacingDetails")
    @ResponseBody
    public List<String> findHorseRacingDetails(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_SE.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/raceRefund")
    @ResponseBody
    public List<String> findRaceRefund(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB15_HR.get(), () -> raceId)
                .stream());
    }

    @GetMapping("/voteCount")
    @ResponseBody
    public List<String> findVoteCount(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB20_H1.get(), () -> raceId)
                .stream());
    }

    /**
     * 単勝・複勝・枠連
     */
    @GetMapping("/winsPlaceBracketQuinella")
    @ResponseBody
    public List<String> findWinsPlaceBracketQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB31_O1.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬連
     */
    @GetMapping("/quinella")
    @ResponseBody
    public List<String> findQuinella(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB32_O2.get(), () -> raceId)
                .stream());
    }

    /**
     * ワイド
     */
    @GetMapping("/quinellaPlace")
    @ResponseBody
    public List<String> findQuinellaPlace(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB33_O3.get(), () -> raceId)
                .stream());
    }

    /**
     * 馬単
     */
    @GetMapping("/exacta")
    @ResponseBody
    public List<String> findExacta(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB34_O4.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連複
     */
    @GetMapping("/trio")
    @ResponseBody
    public List<String> findTrio(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB35_O5.get(), () -> raceId)
                .stream());
    }

    /**
     * 三連単
     */
    @GetMapping("/trifecta")
    @ResponseBody
    public List<String> findTrifecta(@RequestParam("raceId") String raceId) {
        return converter(() -> JvLinkClient
                .readLines(OB36_O6.get(), () -> raceId)
                .stream());
    }

}
