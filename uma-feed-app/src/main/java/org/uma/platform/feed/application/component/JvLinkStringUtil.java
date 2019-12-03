package org.uma.platform.feed.application.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

final public class JvLinkStringUtil {

    private static final String characterCode = "x-SJIS_0213";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 戻りの型が決まっているのでジェネリックは考えない。
     *
     * @param json
     * @return
     */
    public static Map<String, Integer> jsonStringToMap(String json) {
        Objects.requireNonNull(json);
        try {
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap<String, Integer>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static byte[] stringToByte(String str) {
        Objects.requireNonNull(str);
        try {
            return str.getBytes(characterCode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String byteToStringOnSlice(byte[] array, int start, int end) {
        final byte[] slice = Arrays.copyOfRange(array, start, end);
        final ByteBuffer byteBuffer = ByteBuffer.wrap(slice);

        try {
            return Charset.forName(characterCode)
                    .newDecoder()
                    .decode(byteBuffer)
                    .toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

//    // テスト用にいったん入れ込む。
//    private static void test(byte[] array, int start, int end) {
//        try {
//            final byte[] slice = Arrays.copyOfRange(array, start, end);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println(array.length);
//            System.out.println("start: " + start + " end: " + end);
//            throw e;
//        }
//    }


}