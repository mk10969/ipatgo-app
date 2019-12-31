package org.uma.platform.feed.application.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.uma.platform.common.config.spec.RecordSpec;
import org.uma.platform.common.model.RaceRefund;
import org.uma.platform.common.model.VoteCount;
import org.uma.platform.common.model.odds.Quinella;
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


@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class JvStoredRepositoryModelMapperTest {

    private final JvLinkModelMapper jvLinkModelMapper;
    private final ResourceLoader resourceLoader;


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


    private void test(Supplier<Stream<Object>> streamSupplier) {
        streamSupplier.get()
                .map(this::toJson)
                .forEach(System.out::println);
    }

    @SafeVarargs
    private final void testAll(Supplier<Stream<Object>>... streamSupplier) {
        Stream.of(streamSupplier).forEach(this::test);
    }

    @Test
    void test_ALL_デシリアライズテスト() {
        testAll(
//                () -> readTestData("RA")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, RacingDetails.class)),
//                () -> readTestData("SE")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, HorseRacingDetails.class)),
                () -> readTestData("HR")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, RaceRefund.class))
                        .peek(model -> model.getRefundWins().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundPlaces().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundBracketQuinellas().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundQuinellas().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundQuinellaPlaces().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundSpares().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundExactas().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundTrios().removeIf(this::refundFilter))
                        .peek(model -> model.getRefundTrifectas().removeIf(this::refundFilter))
                        .map(i -> i),

//                () -> readTestData("CS")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Course.class)),
//                () -> readTestData("SK")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Offspring.class)),
//                () -> readTestData("HN")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, BreedingHorse.class)),
//                () -> readTestData("BT")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Ancestry.class)),
//                () -> readTestData("BR")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Breeder.class)),
//                () -> readTestData("UM")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, RaceHorse.class)),
//                () -> readTestData("KS")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Jockey.class)),
//                () -> readTestData("CH")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Trainer.class)),
//                () -> readTestData("H1")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, VoteCount.class)),
//                () -> readTestData("O1")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, WinsPlaceBracketQuinella.class)),
                () -> readTestData("O2")
                        .stream()
                        .map(line -> jvLinkModelMapper.deserialize(line, Quinella.class))
                        .peek(model -> model.getQuinellaOdds().removeIf(quinellaOdds ->
                                quinellaOdds.getBetRank() == null && quinellaOdds.getOdds() == null))
                        .peek(model -> System.out.println(model.getQuinellaOdds().size()))
                        .map(i -> i)

//                () -> readTestData("O2")
//                        .stream()
//                        .map(line -> jvLinkModelMapper.deserialize(line, Quinella.class))
//                        .peek(model -> System.out.println(model.getQuinellaOdds().size()))
//                        .map(i -> i)
        );
    }

    @Test
    void test_H1モデルマッパー_データは単一ファイル() {
        // この子は、特別nullを許容しているので。
        readTestData("H1")
                .stream()
                .map(line -> jvLinkModelMapper.deserialize(line, VoteCount.class))
                .peek(model -> model.getVoteCountWins().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountPlaces().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountBracketQuinellas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountQuinellas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountQuinellaPlaces().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountExactas().removeIf(this::voteFilter))
                .peek(model -> model.getVoteCountTrios().removeIf(this::voteFilter))
                .map(this::toJson)
                .forEach(System.out::println);
    }

    private boolean refundFilter(RaceRefund.refund refund) {
        return refund.getBetRank() == null && refund.getRefundMoney() == null;
    }

    private boolean refundFilter(RaceRefund.refundPair refundPair) {
        return refundPair.getBetRank() == null && refundPair.getRefundMoney() == null;
    }

    private boolean refundFilter(RaceRefund.refundTriplet refundTriplet) {
        return refundTriplet.getBetRank() == null && refundTriplet.getRefundMoney() == null;
    }


    private boolean voteFilter(VoteCount.Vote vote) {
        return vote.getBetRank() == null && vote.getVoteCount() == null;
    }

    private boolean voteFilter(VoteCount.VotePair votePair) {
        return votePair.getBetRank() == null && votePair.getVoteCount() == null;
    }

    private boolean voteFilter(VoteCount.VoteTriplet voteTriplet) {
        return voteTriplet.getBetRank() == null && voteTriplet.getVoteCount() == null;
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