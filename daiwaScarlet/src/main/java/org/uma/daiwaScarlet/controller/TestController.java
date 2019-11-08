package org.uma.daiwaScarlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.daiwaScarlet.model.RacingDetailsModel;
import org.uma.daiwaScarlet.service.RacingDetailsService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private RacingDetailsService racingDetailsService;


    @RequestMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping("/ra")
    public List<RacingDetailsModel> ra(){
        ZonedDateTime dateTime = ZonedDateTime.now();
        return racingDetailsService.findAllOnThisWeek(dateTime);
    }





}
