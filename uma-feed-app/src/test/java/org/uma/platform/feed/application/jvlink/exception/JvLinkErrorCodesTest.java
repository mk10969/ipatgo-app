package org.uma.platform.feed.application.jvlink.exception;

import org.junit.jupiter.api.Test;

public class JvLinkErrorCodesTest {

    @Test
    public void test() {
        JvLinkErrorCode aaa = JvLinkErrorCode.of(-203);
        System.out.println(aaa);
    }


//    @Test
//    public void test2() {
//        System.out.println(JvLinkErrorCode._0.isSuccess());
//        System.out.println(JvLinkErrorCode._100.isSuccess());
//    }


}