package org.uma.daiwaScarlet.code;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class RaceGradeCodeTest {

    @Test
    void test_スペースチェック() {
        String code = "null ";
        if (StringUtils.isBlank(code)) {
            System.out.println("ok");
        }

    }


}