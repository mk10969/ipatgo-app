package org.uma.platform.feed.application.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.util.JvLinkStringUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.readAllLines;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JvStoredAllRepositoryTest {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    private ResourceLoader resourceLoader;


    private List<String> readJvlinkData(String filename) throws IOException {
        Path filePath = resourceLoader
                .getResource("classpath:test-data/" + filename)
                .getFile()
                .toPath();
        return readAllLines(filePath, StandardCharsets.US_ASCII);
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
        readJvlinkData("RACE_RA.txt")
                .stream()
                .peek(i -> i.length())
                .map(line -> jvLinkModelMapper.deserialize(line, RacingDetails.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

    @Test
    void test_SEモデルマッパー_データは単一ファイル() throws IOException {
        readJvlinkData("RACE_SE.txt")
                .stream()
                .peek(i -> i.length())
                .map(line -> jvLinkModelMapper.deserialize(line, HorseRacingDetails.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

    @Test
    void test_HRモデルマッパー_データは単一ファイル() throws IOException {
        readJvlinkData("RACE_HR.txt")
                .stream()
                .peek(i -> i.length())
                .map(line -> jvLinkModelMapper.deserialize(line, RaceRefund.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }


    @Test
    void test_ファイルの中身のデータの長さ確認() throws IOException {
        readJvlinkData("RACE_HR.txt")
                .stream()
                .map(i -> JvLinkStringUtil.stringToByte(i))
                .forEach(i -> System.out.println(i.length));
    }


}