package org.uma.platform.feed.application.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

final public class JvLinkStringUtil {

    /**
     * クソみたいな話
     * https://weblabo.oscasierra.net/shift_jis-windows31j/
     * <p>
     * SHIFT-JIS、x-SJIS_0213、ともに、ダメ。
     * java.nio.charset.MalformedInputException: Input length = 1
     */
    private static final Charset SHIFT_JIS = Charset.forName("MS932");

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
            return objectMapper.readValue(json, new TypeReference<LinkedHashMap<String, Integer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static byte[] stringToByte(String str) {
        Objects.requireNonNull(str);
        return str.getBytes(SHIFT_JIS);
    }

    public static String byteToStringOnSlice(byte[] array, int start, int end) {
        final byte[] slice = Arrays.copyOfRange(array, start, end);
        final ByteBuffer byteBuffer = ByteBuffer.wrap(slice);

        try {
            return SHIFT_JIS.newDecoder().decode(byteBuffer).toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


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