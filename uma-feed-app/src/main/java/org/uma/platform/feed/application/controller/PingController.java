package org.uma.platform.feed.application.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.feed.application.util.DateUtil;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PingController {


    @GetMapping("/ping/{id}")
    public String ping(@PathVariable(value = "id") Long id) {
        LocalDateTime aa = DateUtil.tolocalDateTime(id);
        System.out.println(aa);
        return "OK";
    }


}
