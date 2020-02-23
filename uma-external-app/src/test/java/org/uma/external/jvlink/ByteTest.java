package org.uma.external.jvlink;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class ByteTest {

    @Test
    public void test() {

        byte[] byteArray = {83, 69, 55};
        String str_byte = new String(byteArray, StandardCharsets.UTF_8);
        System.out.println(str_byte);

    }

}
