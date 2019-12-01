package org.uma.platform.feed.application.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.*;
import org.uma.platform.feed.application.component.JvLinkModelMapper;
import org.uma.platform.feed.application.component.JvLinkStringUtil;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class JvStoredRepositoryModelMapperTest {

    @Autowired
    private JvLinkModelMapper jvLinkModelMapper;

    @Autowired
    private ResourceLoader resourceLoader;


    private List<String> readTestData(String filename) {
        try {
            return Files.readAllLines(resourceLoader
                            .getResource("classpath:test-data/" + filename)
                            .getFile()
                            .toPath(),
                    Charset.forName("SHIFT-JIS"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ファイルが存在しませんでした");
        }
    }


    private String toJson(Object model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("jsonに変換できませんでした。");
        }
    }

    private Stream<Object> check(Object object) {
        return Stream.of(object.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .map(accessibleField -> {
                    try {
                        // fieldに、nullがセットされていないかチェックする。
                        return Objects.requireNonNull(accessibleField.get(object),
                                accessibleField + "のフィールドの値が、nullです");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("Field: "
                                + accessibleField + " には、アクセスできませんでした。");
                    }
                });
    }

    private void aVoid(Object object) {
        // 空回し
    }


    private void test(Supplier<Stream<Object>> streamSupplier) {
        streamSupplier.get().flatMap(this::check).forEach(this::aVoid);
        streamSupplier.get().map(this::toJson).forEach(System.out::println);
    }

    @SafeVarargs
    private final void testAll(Supplier<Stream<Object>>... streamSupplier) {
        Stream.of(streamSupplier).forEach(this::test);
    }

    @Test
    void test_ALL_デシリアライズテスト() {
        testAll(
                () -> readTestData("RA")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, RacingDetails.class)),
                () -> readTestData("SE")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, HorseRacingDetails.class)),
                () -> readTestData("HR")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, RaceRefund.class)),
                () -> readTestData("CS")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Course.class)),
                () -> readTestData("SK")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Offspring.class)),
                () -> readTestData("HN")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, BreedingHorse.class)),
                () -> readTestData("BT")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Ancestry.class)),
                () -> readTestData("BR")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Breeder.class)),
                () -> readTestData("UM")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, RaceHorse.class)),
                () -> readTestData("KS")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Jockey.class)),
                () -> readTestData("CH")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Trainer.class))

        );

    }

    @Test
    void test_H1モデルマッパー_データは単一ファイル() {
        // この子は、特別nullを許容しているので。
        readTestData("H1")
                .stream()
                .map(line -> jvLinkModelMapper.deserialize(line, VoteCount.class))
                .map(model -> toJson(model))
                .forEach(System.out::println);
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