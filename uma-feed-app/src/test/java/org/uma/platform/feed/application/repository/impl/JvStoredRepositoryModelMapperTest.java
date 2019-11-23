package org.uma.platform.feed.application.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.util.JvLinkStringUtil;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class JvStoredRepositoryModelMapperTest {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    private ResourceLoader resourceLoader;


    private List<String> readJvlinkData(String filename) throws IOException {
        Path filePath = resourceLoader
                .getResource("classpath:test-data/" + filename)
                .getFile()
                .toPath();
        return Files.readAllLines(filePath, StandardCharsets.US_ASCII);
    }

    private Stream<String> readLines(Path filePath) {
        try {
            return Files.lines(filePath, Charset.forName("SHIFT-JIS"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ファイルが存在しませんでした");
        }
    }

    private Path getDirectoryPath() throws IOException {
        return resourceLoader
                .getResource("classpath:test-data/")
                .getFile()
                .toPath();

    }


    private String toJson(Object model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }


    @Test
    void test_RAモデルマッパー_データは単一ファイル() throws IOException {
        readJvlinkData("RA")
                .stream()
                .peek(i -> i.length())
                .map(line -> jvLinkModelMapper.deserialize(line, RacingDetails.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

    @Test
    void test_SEモデルマッパー_データは単一ファイル() throws IOException {
        readJvlinkData("SE")
                .stream()
                .peek(i -> i.length())
                .map(line -> jvLinkModelMapper.deserialize(line, HorseRacingDetails.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

    @Test
    void test_HRモデルマッパー_データは単一ファイル() throws IOException {
        readJvlinkData("HR")
                .stream()
                .peek(i -> i.length())
                .map(line -> jvLinkModelMapper.deserialize(line, RaceRefund.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }


    @Test
    void test_ファイルの中身のデータの長さ確認() throws IOException {
        readJvlinkData("HR")
                .stream()
                .map(i -> JvLinkStringUtil.stringToByte(i))
                .forEach(i -> System.out.println(i.length));
    }

    @Test
    void test_ディレクトリ配下のファイルたちのデータの長さ確認() throws IOException {
        Files.list(getDirectoryPath())
                .flatMap(filePath -> readLines(filePath)
                        .filter(Objects::nonNull)
                        .map(i -> i.replace("\\n", "")) // 仮おきデータを除外
                        .map(line -> JvLinkStringUtil.stringToByte(line).length)
                        .map(i -> Tuples.of(filePath.toFile().getName(), i)))
                .forEach(i ->
                        assertEquals(i.getT2() + 2, RecordSpec.of(i.getT1()).getLength())
                );

    }

}