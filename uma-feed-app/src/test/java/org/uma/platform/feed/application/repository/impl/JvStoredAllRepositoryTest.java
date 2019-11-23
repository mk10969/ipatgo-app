package org.uma.platform.feed.application.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.component.JvLinkModelMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JvStoredAllRepositoryTest {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    private List<String> findByRecordSpec(RecordSpec recordSpec) throws IOException {
        Path path = Paths.get("/Users/m-kakiuchi/myApp/UmaApplication/daiwaScarlet/src/test/java/org/uma/daiwaScarlet/repository/impl/"
                + recordSpec.getCode().toLowerCase()
                + "_data.txt");
        return readAllLines(path, Charset.forName("SHIFT-JIS"));
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
        findByRecordSpec(RecordSpec.RA)
                .stream()
                .map(line -> jvLinkModelMapper.deserialize(line, RacingDetails.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

    @Test
    void test_SEモデルマッパー_データは単一ファイル() throws IOException {
        findByRecordSpec(RecordSpec.SE)
                .stream()
                .map(line -> jvLinkModelMapper.deserialize(line, HorseRacingDetails.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

    @Test
    void test_HRモデルマッパー_データは単一ファイル() throws IOException {
        findByRecordSpec(RecordSpec.HR)
                .stream()
                .map(line -> jvLinkModelMapper.deserialize(line, RaceRefund.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
    }

}