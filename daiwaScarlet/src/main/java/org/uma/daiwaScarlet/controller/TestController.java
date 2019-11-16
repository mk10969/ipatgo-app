package org.uma.daiwaScarlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.daiwaScarlet.model.RaceRefund;
import org.uma.daiwaScarlet.model.RacingDetails;
import org.uma.daiwaScarlet.service.RaceRefundService;
import org.uma.daiwaScarlet.service.RacingDetailsService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TestController {

    private final RacingDetailsService racingDetailsService;
    private final RaceRefundService refundService;
    private final ZonedDateTime dateTime = ZonedDateTime.now().minusWeeks(1L);

    @Autowired
    public TestController(RacingDetailsService racingDetailsService, RaceRefundService refundService) {
        this.racingDetailsService = racingDetailsService;
        this.refundService = refundService;
    }

    @RequestMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping("/ra")
    public List<RacingDetails> ra() {
        return racingDetailsService.findAllOnThisWeek(dateTime);
    }

    @GetMapping("/refund")
    public List<RaceRefund> findAllRefund() {
        return refundService.findAllOnThisWeek(dateTime);
    }


}
