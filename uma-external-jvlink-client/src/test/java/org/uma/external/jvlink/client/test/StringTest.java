package org.uma.external.jvlink.client.test;

import org.junit.jupiter.api.Test;


import java.nio.charset.Charset;

public class StringTest {

    @Test
    void test() {
        Charset aaa = Charset.forName("SJIS");
        System.out.println(aaa.name());

    }

    @Test
    void test2(){
        

        StaticInitializrTest.getOnce();

    }

}