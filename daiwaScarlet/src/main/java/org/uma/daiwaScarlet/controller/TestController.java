package org.uma.daiwaScarlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.daiwaScarlet.context.RecordSpecItems;

@RestController
public class TestController {

    @Autowired
    @Qualifier("RA")
    private RecordSpecItems recordSpecItems;

    @RequestMapping("/test")
    public String test() {

        // 中身確認
        recordSpecItems.getRecordItems().forEach(System.out::println);


        return "OK";

    }

}
