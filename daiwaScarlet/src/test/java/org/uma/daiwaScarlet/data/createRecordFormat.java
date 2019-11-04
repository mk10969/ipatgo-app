package org.uma.daiwaScarlet.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class createRecordFormat {

    private static final String pathName =
            "/Users/m-kakiuchi/myApp/UmaApplication/daiwaScarlet/src/test/java/org/uma/daiwaScarlet/data/";

    @Test
    void test_Format作成() throws IOException {
        createRecordFormat("se.txt");

    }

    private void createRecordFormat(String fileName) throws IOException {
        String prefix = fileName.substring(0, 2);

        Path path = Paths.get(pathName + fileName);
        List<String> stringList = Files.readAllLines(path);

        List<Header> once = stringList.stream()
                .filter(Objects::nonNull)
                .map(i -> i.split("\t"))
                .map(i -> new Header(i[0], i[1], i[2], i[3]))
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        IntStream.range(0, once.size()).forEach(i -> {
            String column = "%s.recordItems[" + i + "].column=" + once.get(i).getColumn();
            String start = "%s.recordItems[" + i + "].start=" + once.get(i).getStart();
            String Length = "%s.recordItems[" + i + "].Length=" + once.get(i).getLength();
            String repeat = "%s.recordItems[" + i + "].repeat=" + once.get(i).getRepeat();

            result.add(String.format(column, prefix));
            result.add(String.format(start, prefix));
            result.add(String.format(Length, prefix));
            result.add(String.format(repeat, prefix));
        });

        result.forEach(System.out::println);
    }

    @Data
    @AllArgsConstructor
    public static class Header {
        private String column;
        private String start;
        private String Length;
        private String repeat;

    }


}
