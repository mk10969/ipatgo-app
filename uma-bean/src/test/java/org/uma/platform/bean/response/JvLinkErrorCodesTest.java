package org.uma.platform.bean.response;

import org.junit.jupiter.api.Test;
import org.uma.platform.bean.exception.JvLinkErrorCode;

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