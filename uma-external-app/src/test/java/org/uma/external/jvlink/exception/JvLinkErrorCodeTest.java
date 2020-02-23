package org.uma.external.jvlink.exception;

import org.junit.jupiter.api.Test;

class JvLinkErrorCodeTest {

    @Test
    public void test() {
        JvLinkErrorCode aaa = JvLinkErrorCode.of(-203);
        System.out.println(aaa);
    }


}