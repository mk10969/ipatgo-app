package org.uma.vodka;

import org.junit.jupiter.api.Test;
import org.uma.vodka.config.Option;
import org.uma.vodka.config.condition.StoredOpenCondition;
import org.uma.vodka.config.spec.RecordSpec;
import org.uma.vodka.config.spec.StoredDataSpec;
import org.uma.vodka.response.JvByteContent;
import org.uma.vodka.response.JvStringContent;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

class JvLinkTest {

//    @Test
//    void readerTest() {
//        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA, Option.THIS_WEEK);
//        ZonedDateTime dateTime = ZonedDateTime.of(2019, 8, 20, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
//
//        try (Stream<JvByteContent> jvReader = JvLink.findAll(condition, dateTime)) {
//            // jvGetsは、クソ遅かった。。。２秒かかる。。。
//            jvReader.forEach(System.out::println);
//        }
//    }

    @Test
    void writerTest() throws IOException {
        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA);
        ZonedDateTime dateTime = ZonedDateTime.of(2019, 8, 20, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));


        Path filePath = Paths.get("C:\\Users\\pbkakiuchi.PB\\myApp\\vodka\\src\\test\\java\\org\\uma\\vodka\\jvlink\\test.txt");

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        long start = System.currentTimeMillis();
//        try (Stream<JvByteContent> jvReader = jvLink.findAll(condition, dateTime)) {
        try (Stream<JvStringContent> jvReader = JvLink.builder(jv ->
                jv.init().open(condition, dateTime, Option.THIS_WEEK).read(condition)
                        .stream()
                        .filter(content -> content.getLine().startsWith(condition.getRecordType().getCode()))
        )) {
            //空回し
            jvReader.forEach(content ->{});
        }
        // 時間計測
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }


    @Test
    void writerTestGachi() {
        StoredOpenCondition condition = new StoredOpenCondition(StoredDataSpec.RACE, RecordSpec.RA);
        ZonedDateTime dateTime = ZonedDateTime.of(2019, 8, 20, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));

        Path filePath = Paths.get("C:\\Users\\pbkakiuchi.PB\\myApp\\vodka\\src\\test\\java\\org\\uma\\vodka\\jvlink\\test.txt");

//        if (!Files.exists(filePath)) {
//            Files.createFile(filePath);
//        }

        long start = System.currentTimeMillis();
        try (Stream<JvStringContent> jvReader = JvLink.builder(jv ->
                jv.init().open(condition, dateTime, Option.THIS_WEEK).read(condition)
                        .stream()
                        .filter(content -> content.getLine().startsWith(condition.getRecordType().getCode())))
        ) {
            jvReader.map(content -> converter(content.getLine()))
                    .forEach(a -> {});
//                    .forEach(line ->
//                            streamWriterToFile(filePath, line)
//                    );
        }
        // 時間計測
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }


    public static void streamWriterToFile(Path filePath, String line) {
        Objects.requireNonNull(line);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            writer.write(line, 0, line.length());
            writer.newLine();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


//    @Test
//    void test111() {
//        String str = "abcdefghijklmnopqrstuvwxyz";
//        List<String> aa = get(str);
//        aa.forEach(System.out::println);
//    }

    public static String converter(String line) {
        Objects.requireNonNull(line);
        List<String> list = new ArrayList<>();
        int[][] number = {
                {0, 2},
                {2, 1},
                {3, 8},
                {11, 4},
                {15, 4},
                {19, 2},
                {21, 2},
                {23, 2},
                {25, 2},
                {27, 1},
                {28, 4},
                {32, 60},
                {92, 60},
                {152, 60},
                {212, 120},
                {332, 120},
                {452, 120},
                {572, 20},
                {592, 12},
                {604, 6},
                {610, 1},
                {611, 3},
                {614, 1},
                {615, 1},
                {616, 2},
                {618, 3},
                {621, 1},
                {622, 3},
                {625, 3},
                {628, 3},
                {631, 3},
                {634, 3},
                {637, 60},
                {697, 4},
                {701, 4},
                {705, 2},
                {707, 2},
                {709, 2},
                {711, 2},
                {713, 8},
                {721, 8},
                {729, 8},
                {737, 8},
                {745, 8},
                {753, 8},
                {761, 8},
                {769, 8},
                {777, 8},
                {785, 8},
                {793, 8},
                {801, 8},
                {809, 8},
                {817, 8},
                {825, 8},
                {833, 8},
                {841, 8},
                {849, 8},
                {857, 8},
                {865, 8},
                {873, 4},
                {877, 4},
                {881, 2},
                {883, 2},
                {885, 2},
                {887, 1},
                {888, 1},
                {889, 1},
                {890, 3},
                {893, 3},
                {896, 3},
                {899, 3},
                {902, 3},
                {905, 3},
                {908, 3},
                {911, 3},
                {914, 3},
                {917, 3},
                {920, 3},
                {923, 3},
                {926, 3},
                {929, 3},
                {932, 3},
                {935, 3},
                {938, 3},
                {941, 3},
                {944, 3},
                {947, 3},
                {950, 3},
                {953, 3},
                {956, 3},
                {959, 3},
                {962, 3},
                {965, 4},
                {969, 3},
                {972, 3},
                {975, 3},
                {978, 3},
                {981, 1},
                {982, 1},
                {983, 70},
                {1053, 1},
                {1054, 1},
                {1055, 70},
                {1125, 1},
                {1126, 1},
                {1127, 70},
                {1197, 1},
                {1198, 1},
                {1199, 70},
                {1269, 1},
                {1270, 2}
        };
        try {
            final byte[] bytes = line.getBytes("x-SJIS_0213");
            final Charset charset = Charset.forName("x-SJIS_0213");
            Stream.of(number).forEach(num -> {
                        final byte[] slice = Arrays.copyOfRange(bytes, num[0], (num[0] + num[1]));
                        final ByteBuffer byteBuffer = ByteBuffer.wrap(slice);
                        try {
//                            list.add(new String(slice, "x-SJIS_0213"));
                            list.add(charset.newDecoder().decode(byteBuffer).toString());
                        } catch (CharacterCodingException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.join(",", list);
    }

}