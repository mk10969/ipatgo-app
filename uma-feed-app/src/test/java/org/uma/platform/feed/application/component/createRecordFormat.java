package org.uma.platform.feed.application.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class createRecordFormat {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void test_Format作成() throws IOException {
        createRecordFormat("cs.txt");
    }

    private void createRecordFormat(String filename) throws IOException {
        Objects.requireNonNull(filename);
        String prefix = filename.substring(0, 2);

        List<Header> once = Files.readAllLines(resourceLoader
                .getResource("classpath:test-record-format/" + filename)
                .getFile()
                .toPath())
                .stream()
                .filter(Objects::nonNull)
                .map(format -> format.split("\t"))
                .map(format -> new Header(format[0], format[1], format[2], format[3]))
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
    private static class Header {
        private String column;
        private String start;
        private String Length;
        private String repeat;
    }


}
