package org.uma.platform.feed.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.service.RaceRefundService;
import org.uma.platform.feed.application.service.RacingDetailsService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TestController {

    private final RacingDetailsService racingDetailsService;
    private final RaceRefundService refundService;
    private final ZonedDateTime dateTime = ZonedDateTime.now().minusWeeks(1L);

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
