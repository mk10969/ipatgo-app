package org.uma.jvLink.core.util;

import java.nio.charset.Charset;
import java.util.Objects;

public class ByteUtil {

    private ByteUtil() {
    }

    /**
     * クソみたいな話
     * https://weblabo.oscasierra.net/shift_jis-windows31j/
     * <p>
     * SHIFT-JIS、x-SJIS_0213、ともに、ダメ。
     * java.nio.charset.MalformedInputException: Input length = 1
     */
    private static final Charset SHIFT_JIS = Charset.forName("MS932");

    public static byte[] toByte(String str) {
        Objects.requireNonNull(str);
        return str.getBytes(SHIFT_JIS);
    }

}
