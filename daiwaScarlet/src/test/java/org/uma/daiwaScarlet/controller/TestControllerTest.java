package org.uma.daiwaScarlet.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.daiwaScarlet.service.RacingDetailsService;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestControllerTest {

    private final ZonedDateTime dateTime = ZonedDateTime.now().minusWeeks(1L);

    @Autowired
    private RacingDetailsService service;

    @Test
    void test_RA(){

        service.findAllOnThisWeek(dateTime);

    }



}