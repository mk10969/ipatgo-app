package org.uma.platform.feed.application.component;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.uma.platform.common.model.*;
import org.uma.platform.common.model.odds.Quinella;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.platform.feed.application.repository.impl.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Profile("setup")
@Slf4j
@Component
@RequiredArgsConstructor
public class JvSetupReactiveCommandLineRunner implements CommandLineRunner {

    /**
     * セットアップデータ種類（選択可能）
     */
    @Value("${setup.dataTypes}")
    private Set<String> dataTypes;

    /**
     * {@link JvSetupRunnerConfiguration}
     */
    private final Map<String, JvSetupReactiveRunner<?>> jvSetupRunners;


    @Override
    public void run(String... args) throws Exception {
        if (dataTypes == null) {
            throw new IllegalStateException("データ種別が選択されていません。");
        }
        log.info("セットアップ開始");

        Flux.fromStream(jvSetupRunners.entrySet().stream())
                .doOnNext(entry -> log.info("実行中: {}", entry.getKey()))
                .filter(entry -> dataTypes.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(JvSetupReactiveRunner::run)
                .subscribeOn(Schedulers.single()) //single thread で回す
                .subscribe(
                        i -> {},
                        e -> log.error("ERROR: ", e),
                        () -> log.info("完了")
                );
    }


    public static class Checker {

        private static ObjectMapper objectMapper = new ObjectMapper();

        // horseWeightは、とりあえず。
        // nullのデータは、div=9 だった。つまりいらんデータ
        private static List<String> excludeList = Lists.newArrayList(
                "horseWeight",
                "changeAmount",
                "voteCountTotalWin",
                "voteCountTotalPlace",
                "voteCountTotalBracketQuinella",
                "voteCountTotalQuinella",
                "voteCountTotalQuinellaPlace",
                "voteCountTotalExacta",
                "voteCountTotalTrio",
                "restoreVoteCountTotalWin",
                "restoreVoteCountTotalPlace",
                "restoreVoteCountTotalBracketQuinella",
                "restoreVoteCountTotalQuinella",
                "restoreVoteCountTotalQuinellaPlace",
                "restoreVoteCountTotalExacta",
                "restoreVoteCountTotalTrio"
        );

        public static void fieldNotNull(Object model) {
            Map<String, Object> json = JsonFlattener.flattenAsMap(toJson(model));
            json.entrySet().stream()
                    .filter(Checker::nullOkFieldName)
                    .forEach(e -> Objects.requireNonNull(e.getValue(), e.getKey() + "が、nullです。"));
        }

        private static boolean nullOkFieldName(Map.Entry<String, Object> stringObjectEntry) {
            return excludeList.stream().noneMatch(i -> i.contains(stringObjectEntry.getKey()));
        }

        private static String toJson(Object model) {
            try {
                return objectMapper.writeValueAsString(model);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("jsonに変換できませんでした。");
            }
        }
    }


    @FunctionalInterface
    private interface JvSetupReactiveRunner<T> {

        Flux<T> run();
    }


    @Profile("setup")
    @Configuration
    @RequiredArgsConstructor
    private static class JvSetupRunnerConfiguration {

        /**
         * 過去データ日付
         * 現在から設定した日付までのデータをセットアップする。
         */
        @Value("${setup.yyyyMMddHHmmss}")
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime dateTime;

        /**
         * Reactive Mongo Client
         */
        private final ReactiveMongoTemplate reactiveTemplate;


        private <T> Flux<T> insert(Tuple2<List<T>, String> tuples) {
            // 一つのドキュメントに対して、transaction別いらない。
            return reactiveTemplate.insert(tuples.getT1(), tuples.getT2());
//                .inTransaction()
//                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
        }

        private <T> Flux<T> logAndNullCheck(T anyModel) {
            return Flux.just(anyModel)
                    .doOnNext(model -> log.info("{}", model))
                    .doOnNext(Checker::fieldNotNull);
        }

        @Bean("RacingDetails")
        public JvSetupReactiveRunner<RacingDetails> jvSetupRunnerRacingDetails(
                JvStoredRacingDetailsRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "racingDetails"))
                    .flatMap(this::insert);
        }

        @Bean("HorseRacingDetails")
        public JvSetupReactiveRunner<HorseRacingDetails> jvSetupRunnerHorseRacingDetails(
                JvStoredHorseRacingDetailsRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(500)
                    .map(chunk -> Tuples.of(chunk, "horseRacingDetails"))
                    .flatMap(this::insert);
        }

        @Bean("RaceRefund")
        public JvSetupReactiveRunner<RaceRefund> jvSetupRunnerRaceRefund(
                JvStoredRaceRefundRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(500)
                    .map(chunk -> Tuples.of(chunk, "raceRefund"))
                    .flatMap(this::insert);
        }

        @Bean("VoteCount")
        public JvSetupReactiveRunner<VoteCount> jvSetupRunnerVoteCount(
                JvStoredVoteCountRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(30)
                    .map(chunk -> Tuples.of(chunk, "voteCount"))
                    .flatMap(this::insert);
        }


        @Bean("WinsPlaceBracketQuinella")
        public JvSetupReactiveRunner<WinsPlaceBracketQuinella> jvSetupRunnerWinsPlaceBracketQuinella(
                JvStoredOddsWinsPlaceBracketQuinellaRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "winsPlaceBracketQuinella"))
                    .flatMap(this::insert);
        }

        @Bean("Quinella")
        public JvSetupReactiveRunner<Quinella> jvSetupRunnerQuinella(
                JvStoredOddsQuinellaRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "quinella"))
                    .flatMap(this::insert);
        }


        @Bean("Ancestry")
        public JvSetupReactiveRunner<Ancestry> jvSetupRunnerAncestry(
                JvStoredAncestryRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "ancestry"))
                    .flatMap(this::insert);
        }

        @Bean("BreedingHorse")
        public JvSetupReactiveRunner<BreedingHorse> jvSetupRunnerBreedingHorse(
                JvStoredBreedingHorseRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "breedingHorse"))
                    .flatMap(this::insert);
        }

        @Bean("Offspring")
        public JvSetupReactiveRunner<Offspring> jvSetupRunnerOffspring(
                JvStoredOffspringRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "offspring"))
                    .flatMap(this::insert);
        }


        @Bean("RaceHorse")
        public JvSetupReactiveRunner<RaceHorse> jvSetupRunnerRaceHorse(
                JvStoredRaceHorseRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "raceHorse"))
                    .flatMap(this::insert);
        }

        @Bean("Jockey")
        public JvSetupReactiveRunner<Jockey> jvSetupRunnerJockey(
                JvStoredJockeyRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "jockey"))
                    .flatMap(this::insert);
        }

        @Bean("Trainer")
        public JvSetupReactiveRunner<Trainer> jvSetupRunnerTrainer(
                JvStoredTrainerRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "trainer"))
                    .flatMap(this::insert);
        }

        @Bean("Owner")
        public JvSetupReactiveRunner<Owner> jvSetupRunnerOwner(
                JvStoredOwnerRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "owner"))
                    .flatMap(this::insert);
        }

        @Bean("Breeder")
        public JvSetupReactiveRunner<Breeder> jvSetupRunnerBreeder(
                JvStoredBreederRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "breeder"))
                    .flatMap(this::insert);
        }

        @Bean("Course")
        public JvSetupReactiveRunner<Course> jvSetupRunnerCourse(
                JvStoredCourseRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .flatMap(this::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "course"))
                    .flatMap(this::insert);
        }

    }

}
