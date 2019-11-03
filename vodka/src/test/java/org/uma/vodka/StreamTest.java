package org.uma.vodka;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    void test() {
        Stream<String> stream = Stream.of("a", "a", "a", "a", "a");
        // steramのiteratorをメソッド参照（Supplier）し、iterableにキャスト。
        for (String s : (Iterable<? extends String>) stream::iterator) {
            System.out.println(s);
        }

    }

    @Test
    void test2() {
        //String a = " ";
        String a = " ";
        byte[] aa = a.getBytes();
        System.out.println(Arrays.toString(aa));
        System.out.println(a);


        StringBuilder sb = new StringBuilder();
        for (byte d : aa) {
            sb.append(String.format("%02X", d));
        }
        String str = sb.toString();
        System.out.println(str);

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

    @Test
    void testWriter() throws IOException {
        Path filePath = Paths.get("/Users/m-kakiuchi/myApp/vodka/src/test/java/org/uma/vodka/jvlink/writer_test.txt");

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        Stream<String> s = Stream.of("aaa", "aaa", "aaa", "aaa", "aaa", "aaa", "aaa");
        s.forEach(line -> streamWriterToFile(filePath, line));

    }


}
